package com.ssafy.saessak.exception;


import com.ssafy.saessak.exception.code.ExceptionCode;

public class GlobalException extends RuntimeException {
    public GlobalException (ExceptionCode errorMessage) {
        super(errorMessage.getMessage());
    }
}
