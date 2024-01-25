package com.ssafy.saessak.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementResponseDto {

    private String kidName;
    private String replacementName;
    private String replacementRelationship;

}
