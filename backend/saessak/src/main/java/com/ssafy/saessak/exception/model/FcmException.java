package com.ssafy.saessak.exception.model;

import com.ssafy.saessak.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class FcmException extends RuntimeException {
    private int status;
    private String code;
    private String message;

    public FcmException(String errorMessage) {super(errorMessage);}

    public FcmException(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
