package com.ssafy.saessak.exception.model;

import com.ssafy.saessak.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private int status;
    private String code;
    private String message;
    public UnAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
    public UnAuthorizedException(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
