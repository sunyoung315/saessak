package com.ssafy.saessak.oauth.authentication;

import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    // 사용자 인증 객체 생성
    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
