package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UnAuthorizedException;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public User getUserByAuthentication() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(()-> new UnAuthorizedException(ExceptionCode.AUTHENTICATION_USER_NOT_FOUND));
    }
}
