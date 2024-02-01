package com.ssafy.saessak.oauth.exception;

public class UnAuthorizedException extends GlobalException{
    public UnAuthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
