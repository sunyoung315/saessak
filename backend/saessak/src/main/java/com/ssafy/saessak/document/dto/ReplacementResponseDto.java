package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementResponseDto {

    private Long replacementId;
    private LocalDate replacementDay;
    private String kidName;
    private boolean replacementCheck;

}
