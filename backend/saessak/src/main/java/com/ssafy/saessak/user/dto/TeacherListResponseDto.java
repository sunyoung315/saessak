package com.ssafy.saessak.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TeacherListResponseDto {

    private Long teacherId;
    private String teacherName;
    private Long kidId;
    private String kidName;
    private String className;
    private String profile;
}
