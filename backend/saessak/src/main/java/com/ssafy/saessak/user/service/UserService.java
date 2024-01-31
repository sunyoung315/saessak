package com.ssafy.saessak.user.service;

import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.dto.KidListResponseDto;
import com.ssafy.saessak.user.dto.KidMappingRequestDto;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    private final AuthenticationService authenticationService;
    private final ParentService parentService;
    private final S3Upload s3Upload;

    @Transactional
    public void mapping(KidMappingRequestDto mappingRequestDto) {
        User user = authenticationService.getUserByAuthentication();
        Optional<Parent> parent = parentRepository.findById(user.getId());
        if(parent.isPresent()) {
            String registCode = new String(Base64.getDecoder().decode(mappingRequestDto.getRegistCode()), StandardCharsets.UTF_8);
            Long mapping_kidId = Long.parseLong(registCode.substring(0, registCode.length()-3));
            parentService.mapping(parent.get().getId(), mapping_kidId);
        }
    }

    @Transactional
    public Long registKid(String kidName, LocalDate kidBirthday, MultipartFile kidProfile) {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        Kid kid = Kid.builder()
                .nickname(kidName)
                .kidBirthday(kidBirthday)
                .classroom(classroom)
                .kidAllergyCheck(false)
                .build();
        kidRepository.save(kid);
        // 프로필 S3 업로드
        try {
            String filePath = s3Upload.upload(kidProfile, "profile");
            kid.uploadProfile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return kid.getId();
    }

    public List<KidListResponseDto> getClassKid() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);
        if (kidList == null) {
            return Collections.emptyList();
        }

        List<KidListResponseDto> kidListResponseDtoList = new ArrayList<>();
        for(Kid k : kidList){
            KidListResponseDto kidListResponseDto = KidListResponseDto.builder()
                    .kidId(k.getId())
                    .kidName(k.getNickname())
                    .kidBirthday(k.getKidBirthday())
                    .kidAllergy(k.getKidAllergy())
                    .kidProfile(k.getProfile())
                    .kidAllergySignature(k.getKidAllergySignature())
                    .kidAllergyDate(k.getKidAllergyDate())
                    .parentId(Optional.ofNullable(k.getParent()).map(Parent::getId).orElse(null))
                    .classroomId(k.getClassroom().getClassroomId())
                    .build();
            kidListResponseDtoList.add(kidListResponseDto);
        }
        return kidListResponseDtoList;
    }

//    // 채팅용, 부모로 채팅 가능한 모든 선생님 조회
//    public List<TeacherListResponseDto> getParentTeacher() {
//        User user = authenticationService.getUserByAuthentication();
//
//        Map<Long, List<Teacher>> teacherList = new HashMap<>();
//
//        List<Kid> kidList = kidRepository.findAllByParent((Parent) user);
//        for(Kid k : kidList){
//            teacherRepository.fin
//            teacherList.put(k.getId(), k.getClassroom().getTeacherList());
//        }
//
//        List<TeacherListResponseDto> teacherListReponseDtoList = new ArrayList<>();
//        for(Long kidId : teacherList.keySet()) {
//            List<Teacher> tlist = teacherList.get(kidId);
//            Kid k = kidRepository.findById(kidId).get();
//            for (Teacher t : tlist) {
//                TeacherListResponseDto teacherListReponseDto = TeacherListResponseDto.builder()
//                        .teacherId(t.getId())
//                        .teacherName(t.getNickname())
//                        .className(t.getClassroom().getClassroomName())
//                        .kidId(k.getId())
//                        .kidName(k.getNickname())
//                        .build();
//                teacherListReponseDtoList.add(teacherListReponseDto);
//            }
//        }
//        return teacherListReponseDtoList;
//    }

    public String getParentToken(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        Parent parent = kid.getParent();
        return parent.getParentDevice();
    }
}
