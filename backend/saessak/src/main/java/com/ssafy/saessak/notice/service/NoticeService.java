package com.ssafy.saessak.notice.service;

import com.ssafy.saessak.notice.domain.Fix;
import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.notice.dto.*;
import com.ssafy.saessak.notice.repository.FixRepository;
import com.ssafy.saessak.notice.repository.NoticeRepository;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final NoticeRepository noticeRepository;
    private final KidRepository kidRepository;
    private final FixRepository fixRepository;
    private final S3Upload s3Uploader;
    private final AuthenticationService authenticationService;

    public NoticeResponseListDto getAllTeacherNotice(int pageNo) {

        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();

        List<NoticeResponseDto> noticeResponseDtoList = new ArrayList<>();

        // 고정한 공지사항 먼저 추가
        List<Fix> fixList = fixRepository.findAllByUser(user);
        for(Fix f: fixList){
            Notice n = f.getNotice();
            NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                    .noticeId(n.getNoticeId())
                    .noticeTitle(n.getNoticeTitle())
                    .fileFlag(n.getNoticeFile() != null)
                    .teacherName(n.getUser().getNickname())
                    .noticeTime(n.getNoticeTime())
                    .noticeFlag(true)
                    .build();
            noticeResponseDtoList.add(noticeResponseDto);
        }
        Collections.sort(noticeResponseDtoList);

        // 고정 안 한 공지사항 나중에 추가
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "noticeId"));
        Page<Notice> noticeList = noticeRepository.findAllByClassroom(classroom, pageable);
        for(Notice n : noticeList){
            boolean flag = fixRepository.findByNoticeAndUser(n, user).isPresent();
            if(!flag){ // 고정 안 한 공지사항만 고른다.
                NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                        .noticeId(n.getNoticeId())
                        .noticeTitle(n.getNoticeTitle())
                        .fileFlag(n.getNoticeFile() != null)
                        .noticeTime(n.getNoticeTime())
                        .teacherName(n.getUser().getNickname())
                        .noticeFlag(false)
                        .build();
                noticeResponseDtoList.add(noticeResponseDto);
            }

        }

        return NoticeResponseListDto.builder()
                .list(noticeResponseDtoList)
                .length(noticeRepository.countByClassroom(classroom))
                .build();
    }

    //10개씩 페이징
    public NoticeResponseListDto getAllParentNotice(Long userId, int pageNo) {

        User user = userRepository.findById(userId).get();
        Classroom classroom = user.getClassroom();

        List<NoticeResponseDto> noticeResponseDtoList = new ArrayList<>();

        // 고정한 공지사항 먼저 추가
        List<Fix> fixList = fixRepository.findAllByUser(user);
        for(Fix f: fixList){
            Notice n = f.getNotice();
            NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                    .noticeId(n.getNoticeId())
                    .noticeTitle(n.getNoticeTitle())
                    .fileFlag(n.getNoticeFile() != null)
                    .teacherName(n.getUser().getNickname())
                    .noticeTime(n.getNoticeTime())
                    .noticeFlag(true)
                    .build();
            noticeResponseDtoList.add(noticeResponseDto);
        }
        Collections.sort(noticeResponseDtoList);

        // 고정 안 한 공지사항 나중에 추가
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "noticeId"));
        Page<Notice> noticeList = noticeRepository.findAllByClassroom(classroom, pageable);
        for(Notice n : noticeList){
            boolean flag = fixRepository.findByNoticeAndUser(n, user).isPresent();
            if(!flag){ // 고정 안 한 공지사항만 고른다.
                NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                        .noticeId(n.getNoticeId())
                        .noticeTitle(n.getNoticeTitle())
                        .fileFlag(n.getNoticeFile() != null)
                        .noticeTime(n.getNoticeTime())
                        .teacherName(n.getUser().getNickname())
                        .noticeFlag(false)
                        .build();
                noticeResponseDtoList.add(noticeResponseDto);
            }

        }

        return NoticeResponseListDto.builder()
                .list(noticeResponseDtoList)
                .length(noticeRepository.countByClassroom(classroom))
                .build();
    }

    public NoticeDetailReponseDto getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).get();
        return NoticeDetailReponseDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .noticeFile(notice.getNoticeFile())
                .noticeFileName(notice.getNoticeFileName())
                .noticeTime(notice.getNoticeTime())
                .build();
    }
    @Transactional
    public Long addNotice(String title, String content,
                          MultipartFile noticeFile) throws IOException {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        if(noticeFile != null) {
            String filePath = s3Uploader.upload(noticeFile, "notice");
            Notice notice = Notice.builder()
                    .classroom(classroom)
                    .noticeFile(filePath)
                    .noticeTitle(title)
                    .user(user)
                    .noticeFileName(noticeFile.getOriginalFilename())
                    .noticeContent(content)
                    .noticeTime(LocalDate.now())
                    .build();
            return noticeRepository.save(notice).getNoticeId();
        }else {
            Notice notice = Notice.builder()
                    .classroom(classroom)
                    .noticeTitle(title)
                    .user(user)
                    .noticeContent(content)
                    .noticeTime(LocalDate.now())
                    .build();
            return noticeRepository.save(notice).getNoticeId();
        }
    }

    @Transactional
    public Long addTeacherFix(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).get();
        User user = authenticationService.getUserByAuthentication();

        Fix fix = Fix.builder()
                .user(user)
                .notice(notice)
                .build();

        return fixRepository.save(fix).getId();
    }

    @Transactional
    public Long addParentFix(FixedRequestDto fixedRequestDto) {
        Notice notice = noticeRepository.findById(fixedRequestDto.getNoticeId()).get();
        User user = userRepository.findById(fixedRequestDto.getKidId()).get();
        Fix fix = Fix.builder()
                .user(user)
                .notice(notice)
                .build();

        return fixRepository.save(fix).getId();
    }

    @Transactional
    public void deleteTeacherFix(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).get();
        User user = authenticationService.getUserByAuthentication();
        Fix fix = fixRepository.findByNoticeAndUser(notice, user).get();
        fixRepository.delete(fix);
    }

    @Transactional
    public void deleteParentFix(FixedRequestDto fixedRequestDto) {
        Notice notice = noticeRepository.findById(fixedRequestDto.getNoticeId()).get();
        User user = userRepository.findById(fixedRequestDto.getKidId()).get();
        Fix fix = fixRepository.findByNoticeAndUser(notice, user).get();
        fixRepository.delete(fix);
    }

    // 하나씩 페이징
    public List<NoticeResponseFixedDto> getAllFixedNotice(Long userId) {

        User user = userRepository.findById(userId).get();
        List<Fix> fixedList = fixRepository.findAllByUser(user);

        List<NoticeResponseFixedDto> noticeResponseDtoList = new ArrayList<>();
        for(Fix f : fixedList){
            Notice n = f.getNotice();
            NoticeResponseFixedDto noticeResponseDto = NoticeResponseFixedDto.builder()
                    .noticeId(n.getNoticeId())
                    .noticeTitle(n.getNoticeTitle())
                    .noticeContent(n.getNoticeContent())
                    .fileFlag(n.getNoticeFile() != null)
                    .noticeTime(n.getNoticeTime())
                    .noticeFlag(true)
                    .build();
            noticeResponseDtoList.add(noticeResponseDto);
        }

        return noticeResponseDtoList;
    }



}
