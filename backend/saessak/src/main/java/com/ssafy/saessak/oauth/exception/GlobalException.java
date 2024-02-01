package com.ssafy.saessak.oauth.exception;


public class GlobalException extends RuntimeException {
    public GlobalException (ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
