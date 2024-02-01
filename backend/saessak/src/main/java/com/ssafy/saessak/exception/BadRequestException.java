package com.ssafy.saessak.exception;

import com.ssafy.saessak.exception.code.ExceptionCode;

public class BadRequestException extends GlobalException {
    public BadRequestException(ExceptionCode errorMessage) {
        super(errorMessage);
    }
}
