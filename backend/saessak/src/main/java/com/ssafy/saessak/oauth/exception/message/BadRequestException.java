package com.ssafy.saessak.oauth.exception.message;

import com.ssafy.saessak.oauth.exception.GlobalException;

public class BadRequestException extends GlobalException {
    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
