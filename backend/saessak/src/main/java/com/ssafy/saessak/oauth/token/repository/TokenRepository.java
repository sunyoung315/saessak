package com.ssafy.saessak.oauth.token.repository;

import com.ssafy.saessak.oauth.token.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByRefreshToken(final String refreshToken);
}
