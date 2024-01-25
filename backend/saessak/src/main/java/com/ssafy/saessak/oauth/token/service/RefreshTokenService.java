package com.ssafy.saessak.oauth.token.service;

import com.google.api.gax.rpc.NotFoundException;
import com.ssafy.saessak.oauth.token.domain.Token;
import com.ssafy.saessak.oauth.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        if(token.isPresent()) {
            return token.get().getTokenId();
        } else {
            return 0L;
        }
    }

    @Transactional
    public void deleteRefreshToken(final Long userId) {
        Token token = tokenRepository.findById(userId).get();
        tokenRepository.delete(token);
    }
}
