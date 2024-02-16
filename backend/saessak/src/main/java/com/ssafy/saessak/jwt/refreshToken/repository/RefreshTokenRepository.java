package com.ssafy.saessak.jwt.refreshToken.repository;

import com.ssafy.saessak.jwt.refreshToken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(final String refreshToken);
}
