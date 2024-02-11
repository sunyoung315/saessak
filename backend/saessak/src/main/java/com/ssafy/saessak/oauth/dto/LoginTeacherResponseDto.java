package com.ssafy.saessak.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginTeacherResponseDto {

    private String teacherName;
    private Boolean isTeacher;
    private Boolean isAlarm;
    private String teacherProfile;
    private String accessToken;
    private String refreshToken;
    private String classroomName;
}
