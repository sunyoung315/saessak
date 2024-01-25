package com.ssafy.saessak.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginSuccessResponseDto {

    private Long userId;
    private String accessToken;
    private String refreshToken;

}