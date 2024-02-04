package com.ssafy.saessak.fcm.dto;

import com.ssafy.saessak.fcm.domain.FcmToken;
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

    public static FcmResponseDto of (
            final String token,
            final String title,
            final String body
    ) {
        return FcmResponseDto.builder()
                .token(token)
                .title(title)
                .body(body)
                .build();
    }
}
