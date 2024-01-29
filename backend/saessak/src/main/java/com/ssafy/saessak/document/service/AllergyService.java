package com.ssafy.saessak.document.service;

import com.ssafy.saessak.document.dto.AllergyDetailResponseDto;
import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.document.dto.AllergyResponseDto;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AllergyService {

    private final KidRepository kidRepository;
    private final ClassroomRepository classroomRepository;

    @Transactional
    public void insert(AllergyRequestDto requestDto) {
        Kid kid = kidRepository.findById(requestDto.getKidId()).get();
        kid.setAllergy(requestDto.getKidAllergy(), requestDto.getKidAllergySignature(), LocalDate.now());
    }

    public List<AllergyResponseDto> list(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
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
        Kid kid = kidRepository.findById(kidId).get();

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
        Kid kid = kidRepository.findById(kidId).get();
        kid.changeCheck();
    }
}
