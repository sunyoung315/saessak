package com.ssafy.saessak.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReplacementTeacherAlarmResponseDto {

    private Long replacementId;
    private Long classroomId;
    private Long kidId;
    private String kidName;

}
