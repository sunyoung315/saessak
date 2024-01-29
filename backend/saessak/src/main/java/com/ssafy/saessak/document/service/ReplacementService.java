package com.ssafy.saessak.document.service;

import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.document.dto.ReplacementAlarmResponseDto;
import com.ssafy.saessak.document.dto.ReplacementDetailResponseDto;
import com.ssafy.saessak.document.dto.ReplacementRequestDto;
import com.ssafy.saessak.document.dto.ReplacementResponseDto;
import com.ssafy.saessak.document.repository.ReplacementRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplacementService {

    private final ReplacementRepository replacementRepository;
    private final KidRepository kidRepository;
    private final ClassroomRepository classroomRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public ReplacementAlarmResponseDto insert(ReplacementRequestDto requestDto) {
        Kid kid = kidRepository.findById(requestDto.getKidId()).get();

        Replacement replacement = Replacement.builder()
                .kid(kid)
                .replacementDate(requestDto.getReplacementDate())
                .replacementTime(requestDto.getReplacementTime())
                .replacementVehicle(requestDto.getReplacementVehicle())
                .replacementRelationship(requestDto.getReplacementRelationship())
                .replacementNumber(requestDto.getReplacementNumber())
                .replacementName(requestDto.getReplacementName())
                .replacementSignature(requestDto.getReplacementSignature())
                .replacementCheck(false)
                .replacementDay(LocalDate.now())
                .build();

        Replacement savedReplacement = replacementRepository.save(replacement);

        Classroom classroom = kid.getClassroom();
        List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);

        List<String> teacherDeviceList = new ArrayList<>();
        for(Teacher teacher : teacherList) {
            teacherDeviceList.add(teacher.getTeacherDevice());
        }

        ReplacementAlarmResponseDto replacementAlarmResponseDto = ReplacementAlarmResponseDto.builder()
                .replacementId(savedReplacement.getReplacementId())
                .teacherAlarmDeviceList(teacherDeviceList)
                .kidId(savedReplacement.getKid().getId())
                .kidName(savedReplacement.getKid().getNickname())
                .build();

        return replacementAlarmResponseDto;
    }

    public List<ReplacementResponseDto> listOfkidId(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        List<Replacement> replacementList = replacementRepository.findByKid(kid);

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

    public List<ReplacementResponseDto> listOfclassroomId(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);

        List<ReplacementResponseDto> replacementResponseDtoList = new ArrayList<>();

        for(Kid kid : kidList) {
            List<Replacement> replacementList = replacementRepository.findByKid(kid);

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
        Replacement replacement = replacementRepository.findById(replacementId).get();

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

    @Transactional
    public void changeCheck(Long replacementId) {
        Replacement replacement = replacementRepository.findById(replacementId).get();
        replacement.changeCheck();
    }

}
