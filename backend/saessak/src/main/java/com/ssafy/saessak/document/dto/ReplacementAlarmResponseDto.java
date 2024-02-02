package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementAlarmResponseDto {

    private Long replacementId;
    private String teacherDevice;
    private Long kidId;
    private String kidName;

}
