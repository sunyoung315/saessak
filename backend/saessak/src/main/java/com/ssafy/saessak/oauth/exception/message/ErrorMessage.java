package com.ssafy.saessak.oauth.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "액세스 토큰이 만료되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저는 존재하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저의 리프레시 토큰은 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류입니다."),
    TOKEN_INCORRECT_ERROR(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 잘못되었습니다."),
    AUTHENTICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "인가 코드가 만료되었습니다."),
    ;
    int status;
    String message;
}
