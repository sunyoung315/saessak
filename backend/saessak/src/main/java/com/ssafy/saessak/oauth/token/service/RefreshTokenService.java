package com.ssafy.saessak.oauth.token.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UnAuthorizedException;
import com.ssafy.saessak.oauth.token.domain.Token;
import com.ssafy.saessak.oauth.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenRepository tokenRepository;

    @Transactional
    public void saveRefreshToken (final Long userId, final String refreshToken) {
        tokenRepository.save(Token.of( userId, refreshToken));
    }

    public Long findIdByRefreshToken(final String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UnAuthorizedException(ExceptionCode.TOKEN_INCORRECT_ERROR));
        return token.getTokenId();
    }

    @Transactional
    public void deleteRefreshToken(final Long userId) {
        Token token = tokenRepository.findById(userId)
                        .orElseThrow(() -> new UnAuthorizedException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));
        tokenRepository.delete(token);
    }
}
