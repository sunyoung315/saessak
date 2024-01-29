package com.ssafy.saessak.user.service;

import com.google.api.client.util.Value;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.dto.KakaoResponseDto;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.dto.KidListResponseDto;
import com.ssafy.saessak.user.dto.KidRegistRequestDto;
import com.ssafy.saessak.user.dto.ParentRegistRequestDto;
import com.ssafy.saessak.user.dto.TeacherListReponseDto;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    private final ClassroomRepository classroomRepository;

    @Transactional
    public KidListResponseDto mapping(Long parentId, Long kidId) {
        Parent parent = parentRepository.findById(parentId).get();
        Kid kid = kidRepository.findById(kidId).get();
        parent.getKidList().add(kid);
        Kid k = kid.updateParent(parent);

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
        return kidListResponseDto;
    }

//    public List<KidListResponseDto> getClassKid(Long classroomId) {
//        Classroom classroom = classroomRepository.findById(classroomId).get();
//
//        if (classroom.getKidList() == null) {
//            return Collections.emptyList();
//        }
//
//        List<KidListResponseDto> kidListResponseDtoList = new ArrayList<>();
//        for(Kid k : classroom.getKidList()){
//            KidListResponseDto kidListResponseDto = KidListResponseDto.builder()
//                    .kidId(k.getId())
//                    .kidName(k.getNickname())
//                    .kidBirthday(k.getKidBirthday())
//                    .kidAllergy(k.getKidAllergy())
//                    .kidProfile(k.getProfile())
//                    .kidAllergySignature(k.getKidAllergySignature())
//                    .kidAllergyDate(k.getKidAllergyDate())
//                    .parentId(Optional.ofNullable(k.getParent()).map(Parent::getId).orElse(null))
//                    .classroomId(k.getClassroom().getClassroomId())
//                    .build();
//            kidListResponseDtoList.add(kidListResponseDto);
//        }
//        return kidListResponseDtoList;
//    }
//
//    // 채팅용, 부모로 채팅 가능한 모든 선생님 조회
//    public List<TeacherListReponseDto> getParentTeacher(Long parentId) {
//        Parent parent = parentRepository.findById(parentId).get();
//        Map<Long, List<Teacher>> teacherList = new HashMap<>();
//        for(Kid k : parent.getKidList()){
//            teacherList.put(k.getId(), k.getClassroom().getTeacherList());
//        }
//
//        List<TeacherListReponseDto> teacherListReponseDtoList = new ArrayList<>();
//        for(Long kidId : teacherList.keySet()) {
//            List<Teacher> tlist = teacherList.get(kidId);
//            Kid k = kidRepository.findById(kidId).get();
//            for (Teacher t : tlist) {
//                TeacherListReponseDto teacherListReponseDto = TeacherListReponseDto.builder()
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
