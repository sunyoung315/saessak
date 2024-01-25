package com.ssafy.saessak.oauth.exception;

import com.ssafy.saessak.oauth.exception.message.ErrorMessage;

public class UnAuthorizedException extends GlobalException{
    public UnAuthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
