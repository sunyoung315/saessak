package com.ssafy.saessak.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Parent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class KidListResponseDto {
    private Long kidId;
    private String kidName;
    private LocalDate kidBirthday;
    private String kidAllergy;
    private String kidProfile; // s3 url 예정
    private String kidAllergySignature;
    private String kidInviteLink;
    private LocalDate kidAllergyDate;
    private Long parentId;
    private Long classroomId;
}
