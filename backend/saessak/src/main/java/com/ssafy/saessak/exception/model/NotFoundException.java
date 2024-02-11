package com.ssafy.saessak.exception.model;

import com.ssafy.saessak.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private int status;
    private String code;
    private String message;
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
    public NotFoundException(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
