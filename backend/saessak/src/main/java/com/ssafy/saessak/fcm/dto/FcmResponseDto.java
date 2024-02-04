package com.ssafy.saessak.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class FcmResponseDto {

    private String token;
    private String title;
    private String body;
}
