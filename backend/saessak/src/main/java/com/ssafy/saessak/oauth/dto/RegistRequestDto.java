package com.ssafy.saessak.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistRequestDto {

    private Long userId;
    private String registCode;

}
