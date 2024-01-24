package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementDetailResponseDto {

    private Long replacementId;
    private LocalDate replacementDate;
    private String replacementTime;
    private String replacementVehicle;
    private String replacementRelationship;
    private String replacementNumber;
    private String replacementName;
    private String replacementSignature;
    private boolean replacementCheck;
    private LocalDate replacementDay;

}
