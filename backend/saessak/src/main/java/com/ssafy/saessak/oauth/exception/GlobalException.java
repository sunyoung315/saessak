package com.ssafy.saessak.oauth.exception;


import com.ssafy.saessak.oauth.exception.message.ErrorMessage;

public class GlobalException extends RuntimeException {
    public GlobalException (ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
