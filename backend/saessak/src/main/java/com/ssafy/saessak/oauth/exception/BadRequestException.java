package com.ssafy.saessak.oauth.exception;

public class BadRequestException extends GlobalException {
    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
