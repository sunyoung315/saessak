package com.ssafy.saessak.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReplacementRequestDto {

    private Long kidId;
    private LocalDate replacementDate;
    private String replacementTime;
    private String replacementVehicle;
    private String replacementRelationship;
    private String replacementNumber;
    private String replacementName;
    private String replacementSignature;

}
