package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementAlarmResponseDto {

    private Long replacementId;
    private String teacherAlarmDevice;
    private Long kidId;
    private String kidName;

}
