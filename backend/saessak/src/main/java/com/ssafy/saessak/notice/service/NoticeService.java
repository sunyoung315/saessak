package com.ssafy.saessak.notice.service;

import com.ssafy.saessak.notice.domain.Fix;
import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.notice.dto.*;
import com.ssafy.saessak.notice.repository.FixRepository;
import com.ssafy.saessak.notice.repository.NoticeRepository;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final ClassroomRepository classroomRepository;
    private final NoticeRepository noticeRepository;
    private final KidRepository kidRepository;
    private final FixRepository fixRepository;
    private final S3Upload s3Uploader;

    // 20개씩 페이징
    public List<NoticeResponseDto> getAllNotice(Long kidId, Pageable pageable) {

        Kid kid = kidRepository.findById(kidId).get();
        Classroom classroom = kid.getClassroom();
        List<Notice> noticeList = noticeRepository.findAllByClassroom(classroom, pageable);

        List<NoticeResponseDto> noticeResponseDtoList = new ArrayList<>();
        for(Notice n : noticeList){
            boolean flag = fixRepository.findByNoticeAndKid(n, kid).isPresent();
            NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                    .noticeId(n.getNoticeId())
                    .noticeTitle(n.getNoticeTitle())
                    .fileFlag(n.getNoticeFile() != null)
                    .noticeTime(n.getNoticeTime())
                    .noticeFlag(flag)
                    .build();
            noticeResponseDtoList.add(noticeResponseDto);
        }

        return noticeResponseDtoList;
    }

    public NoticeDetailReponseDto getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).get();
        return NoticeDetailReponseDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .noticeFile(notice.getNoticeFile())
                .noticeTime(notice.getNoticeTime())
                .build();
    }
    @Transactional
    public Long addNotice(Long classroomId, String title, String content,
                          MultipartFile noticeFile) throws IOException {

        Classroom classroom = classroomRepository.findById(classroomId).get();
        String filePath = s3Uploader.upload(noticeFile, "notice");

        Notice notice = Notice.builder()
                .classroom(classroom)
                .noticeFile(filePath)
                .noticeTitle(title)
                .noticeContent(content)
                .noticeTime(LocalDate.now())
                .build();

        return noticeRepository.save(notice).getNoticeId();
    }

    @Transactional
    public Long addFix(FixedRequestDto fixedRequestDto) {
        Notice notice = noticeRepository.findById(fixedRequestDto.getNoticeId()).get();
        Kid kid = kidRepository.findById(fixedRequestDto.getKidId()).get();

        Fix fix = Fix.builder()
                .kid(kid)
                .notice(notice)
                .build();

        return fixRepository.save(fix).getId();
    }

    // 하나씩 페이징
    public List<NoticeResponseDto> getAllFixedNotice(Long kidId, Pageable pageable) {

        Kid kid = kidRepository.findById(kidId).get();
        List<Fix> fixedList = fixRepository.findAllByKid(kid, pageable);

        List<NoticeResponseDto> noticeResponseDtoList = new ArrayList<>();
        for(Fix f : fixedList){
            Notice n = f.getNotice();
            NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
                    .noticeId(n.getNoticeId())
                    .noticeTitle(n.getNoticeTitle())
                    .fileFlag(n.getNoticeFile() != null)
                    .noticeTime(n.getNoticeTime())
                    .noticeFlag(true)
                    .build();
            noticeResponseDtoList.add(noticeResponseDto);
        }

        return noticeResponseDtoList;
    }



}
