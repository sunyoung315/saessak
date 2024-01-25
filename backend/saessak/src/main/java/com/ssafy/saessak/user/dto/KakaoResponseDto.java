package com.ssafy.saessak.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KakaoResponseDto {

    private Long id;
    private String email;
    private String nickname;
}
