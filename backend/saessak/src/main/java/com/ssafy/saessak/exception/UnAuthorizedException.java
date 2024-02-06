package com.ssafy.saessak.exception;

import com.ssafy.saessak.exception.code.ExceptionCode;

public class UnAuthorizedException extends GlobalException {
    public UnAuthorizedException(ExceptionCode errorMessage) {
        super(errorMessage);
    }
}
