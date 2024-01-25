package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;

public abstract class SocialService {
    public abstract LoginSuccessResponseDto login(final String authorizationCode);
}
