package com.ssafy.saessak.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReplacementRequestDto {

    private Long kidId;
    private String replacementDate;
    private String replacementTime;
    private String replacementVehicle;
    private String replacementRelationship;
    private String replacementNumber;
    private String replacementName;

}
