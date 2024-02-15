package com.ssafy.saessak.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KidResponseDto {

    private Long kidId;
    private String kidName;
    private String kidProfile;
    private String classroomName;
}
