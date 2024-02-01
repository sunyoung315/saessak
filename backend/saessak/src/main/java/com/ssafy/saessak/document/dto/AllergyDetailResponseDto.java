package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AllergyDetailResponseDto {

    private Long kidId;
    private String classroomName;
    private String kidName;
    private String kidAllergy;
    private String kidAllergySignature;
}
