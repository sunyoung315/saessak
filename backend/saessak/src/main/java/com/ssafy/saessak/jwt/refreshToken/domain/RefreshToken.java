package com.ssafy.saessak.jwt.refreshToken.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 1000L * 14)
@AllArgsConstructor
@Getter
@Builder
public class RefreshToken {

    @Id
    private Long tokenId;

    private String refreshToken;

    public static RefreshToken of (
            final Long tokenId,
            final String refreshToken
    ) {
        return RefreshToken.builder()
                .tokenId(tokenId)
                .refreshToken(refreshToken)
                .build();
    }
}
