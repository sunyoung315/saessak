package com.ssafy.saessak.jwt.refreshToken.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UnAuthorizedException;
import com.ssafy.saessak.jwt.refreshToken.domain.RefreshToken;
import com.ssafy.saessak.jwt.refreshToken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken (final Long userId, final String refreshToken) {
        refreshTokenRepository.save(RefreshToken.of( userId, refreshToken));
    }

    public Long findIdByRefreshToken(final String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UnAuthorizedException(ExceptionCode.TOKEN_INCORRECT_ERROR));
        return token.getTokenId();
    }

    @Transactional
    public void deleteRefreshToken(final Long userId) {
        RefreshToken token = refreshTokenRepository.findById(userId)
                        .orElseThrow(() -> new UnAuthorizedException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenRepository.delete(token);
    }
}
