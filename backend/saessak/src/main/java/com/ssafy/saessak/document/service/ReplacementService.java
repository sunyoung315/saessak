package com.ssafy.saessak.document.service;

import com.ssafy.saessak.attendance.dto.ReplacementParentAlarmResponseDto;
import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.document.dto.ReplacementTeacherAlarmResponseDto;
import com.ssafy.saessak.document.dto.ReplacementDetailResponseDto;
import com.ssafy.saessak.document.dto.ReplacementRequestDto;
import com.ssafy.saessak.document.dto.ReplacementResponseDto;
import com.ssafy.saessak.document.repository.ReplacementRepository;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.menu;

@Service
@RequiredArgsConstructor
public class ReplacementService {

    private final ReplacementRepository replacementRepository;
    private final KidRepository kidRepository;
    private final AuthenticationService authenticationService;
    private final FcmService fcmService;
    private final S3Upload s3Uploader;

    @Transactional
    public Long insert(ReplacementRequestDto requestDto) {
        Kid kid = kidRepository.findById(requestDto.getKidId())
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));

        Replacement replacement = Replacement.builder()
                .kid(kid)
                .replacementDate(LocalDate.parse(requestDto.getReplacementDate()))
                .replacementTime(requestDto.getReplacementTime())
                .replacementVehicle(requestDto.getReplacementVehicle())
                .replacementRelationship(requestDto.getReplacementRelationship())
                .replacementNumber(requestDto.getReplacementNumber())
                .replacementName(requestDto.getReplacementName())
                .replacementCheck(false)
                .replacementDay(LocalDate.now())
                .build();

        Replacement savedReplacement = replacementRepository.save(replacement);
        
        // 입력과 동시에 아이의 반 선생님 모두에게 알림 전송
        Classroom classroom = kid.getClassroom();
        ReplacementTeacherAlarmResponseDto replacementAlarmResponseDto = ReplacementTeacherAlarmResponseDto.builder()
                .replacementId(savedReplacement.getReplacementId())
                .classroomId(classroom.getClassroomId())
                .kidId(savedReplacement.getKid().getId())
                .kidName(savedReplacement.getKid().getNickname())
                .build();

        fcmService.sendInsertReplacement(replacementAlarmResponseDto);

        return savedReplacement.getReplacementId();
    }

    @Transactional
    public void insertSign(Long replacementId, MultipartFile signFile) throws IOException {
        Replacement replacement = replacementRepository.findById(replacementId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.REPLACEMENT_NOT_FOUND));
        // AWS S3 사진 upload
        String filePath = s3Uploader.upload(signFile, "replacement");
        replacement.uploadSign(filePath);
    }

    public List<ReplacementResponseDto> listOfkidId(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        List<Replacement> replacementList = replacementRepository.findByKidOrderByReplacementDay(kid);

        List<ReplacementResponseDto> replacementResponseDtoList = new ArrayList<>();

        for(Replacement replacement : replacementList) {
            ReplacementResponseDto replacementResponseDto = ReplacementResponseDto.builder()
                    .replacementId(replacement.getReplacementId())
                    .replacementDay(replacement.getReplacementDay())
                    .kidName(kid.getNickname())
                    .replacementCheck(replacement.isReplacementCheck())
                    .build();

            replacementResponseDtoList.add(replacementResponseDto);
        }

        return replacementResponseDtoList;
    }

    public List<ReplacementResponseDto> listOfclassroomId() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);

        List<ReplacementResponseDto> replacementResponseDtoList = new ArrayList<>();

        for(Kid kid : kidList) {
            List<Replacement> replacementList = replacementRepository.findByKidOrderByReplacementDay(kid);

            for(Replacement replacement : replacementList) {
                ReplacementResponseDto replacementResponseDto = ReplacementResponseDto.builder()
                        .replacementId(replacement.getReplacementId())
                        .replacementDay(replacement.getReplacementDay())
                        .kidName(kid.getNickname())
                        .replacementCheck(replacement.isReplacementCheck())
                        .build();

                replacementResponseDtoList.add(replacementResponseDto);
            }
        }

        return replacementResponseDtoList;
    }

    public ReplacementDetailResponseDto detail(Long replacementId) {
        Replacement replacement = replacementRepository.findById(replacementId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.REPLACEMENT_NOT_FOUND));

        ReplacementDetailResponseDto replacementDetailResponseDto = ReplacementDetailResponseDto.builder()
                .replacementId(replacement.getReplacementId())
                .replacementDate(replacement.getReplacementDate())
                .replacementTime(replacement.getReplacementTime())
                .replacementVehicle(replacement.getReplacementVehicle())
                .replacementRelationship(replacement.getReplacementRelationship())
                .replacementNumber(replacement.getReplacementNumber())
                .replacementName(replacement.getReplacementName())
                .replacementSignature(replacement.getReplacementSignature())
                .replacementCheck(replacement.isReplacementCheck())
                .replacementDay(replacement.getReplacementDay())
                .build();

        return replacementDetailResponseDto;
    }

    public ReplacementParentAlarmResponseDto checkReplacement(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        List<Replacement> replacementList = replacementRepository.findByKidOrderByReplacementDay(kid);

        boolean check = false;
        for(Replacement replacement : replacementList) {
            if(replacement.getReplacementDate()==LocalDate.now()) {
                LocalTime replacementTime = LocalTime.parse(replacement.getReplacementTime());
                long timegap = ChronoUnit.MINUTES.between(replacementTime, LocalTime.now());
                if(Math.abs(timegap) <= 15) {
                    ReplacementParentAlarmResponseDto replacementParentAlarmResponseDto = ReplacementParentAlarmResponseDto.builder()
                            .kidName(kid.getNickname())
                            .replacementName(replacement.getReplacementName())
                            .replacementRelationship(replacement.getReplacementRelationship())
                            .build();
                    return replacementParentAlarmResponseDto;
                }
            }
        }

        return null;
    }

    @Transactional
    public void changeCheck(Long replacementId) {
        Replacement replacement = replacementRepository.findById(replacementId).get();
        replacement.changeCheck();
    }

}
