package com.ssafy.saessak.exception.model;

import com.ssafy.saessak.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class FixOverException extends RuntimeException {

    private int status;
    private String code;
    private String message;

    public FixOverException(String errorMessage) {super(errorMessage);}

    public FixOverException(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

}
