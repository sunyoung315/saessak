package com.ssafy.saessak.fcm.domain;

import com.ssafy.saessak.jwt.refreshToken.domain.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "fcmToken", timeToLive = 60 * 60 * 24 * 1000L * 14)
@AllArgsConstructor
@Getter
@Builder
public class FcmToken {

    @Id
    private Long tokenId;

    private String fcmToken;

    public static FcmToken of (
            final Long tokenId,
            final String fcmToken
    ) {
        return FcmToken.builder()
                .tokenId(tokenId)
                .fcmToken(fcmToken)
                .build();
    }

}
