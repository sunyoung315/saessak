package com.ssafy.saessak.document.service;

import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.document.dto.AllergyDetailResponseDto;
import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.document.dto.AllergyResponseDto;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyService {

    private final KidRepository kidRepository;
    private final AuthenticationService authenticationService;
    private final S3Upload s3Uploader;

    @Transactional
    public void insert(AllergyRequestDto requestDto) {
        Kid kid = kidRepository.findById(requestDto.getKidId())
                        .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        kid.setAllergy(requestDto.getKidAllergy(), LocalDate.now());
    }

    @Transactional
    public void insertSign(Long kidId, MultipartFile signFile) throws IOException {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.KID_NOT_FOUND));
        // AWS S3 사진 upload
        String filePath = s3Uploader.upload(signFile, "allergy");
        kid.uploadSign(filePath);
    }

    public List<AllergyResponseDto> list() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);

        List<AllergyResponseDto> allgeryResponseDtoList = new ArrayList<>();

        for(Kid kid : kidList) {
            AllergyResponseDto allergyResponseDto = AllergyResponseDto.builder()
                    .kidId(kid.getId())
                    .kidName(kid.getNickname())
                    .kidAllergyDate(kid.getKidAllergyDate())
                    .kidAllergyCheck(kid.getKidAllergyCheck())
                    .build();

            allgeryResponseDtoList.add(allergyResponseDto);
        }

        return allgeryResponseDtoList;
    }

    public AllergyDetailResponseDto detail(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));

        AllergyDetailResponseDto allergyDetailResponseDto = AllergyDetailResponseDto.builder()
                .kidId(kid.getId())
                .classroomName(kid.getClassroom().getClassroomName())
                .kidName(kid.getNickname())
                .kidAllergy(kid.getKidAllergy())
                .kidAllergySignature(kid.getKidAllergySignature())
                .build();

        return allergyDetailResponseDto;
    }

    @Transactional
    public void changeCheck(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                        .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        kid.changeCheck();
    }
}
