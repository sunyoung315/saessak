package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class AllergyResponseDto {

    private Long kidId;
    private String kidName;
    private LocalDate kidAllergyDate;
    private boolean kidAllergyCheck;
}
