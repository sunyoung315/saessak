package com.ssafy.saessak.fcm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmTokenRequestDto {

    private Long id;
    private String token;
}
