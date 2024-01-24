package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponse;

public abstract class SocialService {
    public abstract LoginSuccessResponse login(final String authorizationCode);
}
