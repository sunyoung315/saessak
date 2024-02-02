package com.ssafy.saessak.exception.handler;

import com.ssafy.saessak.exception.model.FcmException;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UnAuthorizedException;
import com.ssafy.saessak.exception.dto.ExceptionDto;
import com.ssafy.saessak.exception.model.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.ssafy.saessak")
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<?> handleUserException(UserException exception) {
        log.info("ExceptionHandler UserException 진입");
        exception.printStackTrace();
        return ExceptionDto.toResponseEntity(exception.getStatus(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<?> handleUnAuthException(UnAuthorizedException exception) {
        log.info("ExceptionHandler UnAuthException 진입");
        exception.printStackTrace();
        return ExceptionDto.toResponseEntity(exception.getStatus(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        log.info("ExceptionHandler NotFoundException 진입");
        exception.printStackTrace();
        return ExceptionDto.toResponseEntity(exception.getStatus(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(FcmException.class)
    protected ResponseEntity<?> handleFcmException(FcmException exception) {
        log.info("ExceptionHandler FcmException 진입");
        exception.printStackTrace();
        return ExceptionDto.toResponseEntity(exception.getStatus(), exception.getCode(), exception.getMessage());
    }
}
