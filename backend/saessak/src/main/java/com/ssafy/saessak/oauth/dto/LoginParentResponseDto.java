package com.ssafy.saessak.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class LoginParentResponseDto {

    private boolean isTeacher;
    private String accessToken;
    private String refreshToken;
    private List<KidResponseDto> kidList;

}
