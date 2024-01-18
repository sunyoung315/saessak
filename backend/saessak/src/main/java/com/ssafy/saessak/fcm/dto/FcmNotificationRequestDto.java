package com.ssafy.saessak.fcm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FcmNotificationRequestDto {

    private Notification notification;

    @Valid
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Notification {
        // 각각 벨리데이션으로 유효성 검사.
        @NotNull(message = "토큰을 정확히 입력해주세요.")
        private String token;
        @NotNull(message = "제목을 정확히 입력해주세요.")
        private String title;
        @NotNull(message = "내용을 정확히 입력해주세요.")
        private String body;
    }
}
