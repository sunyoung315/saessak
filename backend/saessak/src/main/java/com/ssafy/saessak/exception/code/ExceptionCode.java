package com.ssafy.saessak.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    INVALID_JWT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A101", "유효하지 않은 엑세스 토큰입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "A102", "액세스 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A103", "지원하지 않는 엑세스 토큰입니다."),
    EMPTY_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "A104", "토큰이 존재하지 않습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R101",  "해당 유저의 리프레시 토큰은 존재하지 않습니다."),
    TOKEN_INCORRECT_ERROR(HttpStatus.UNAUTHORIZED.value(), "R102", "리프레시 토큰이 잘못되었습니다."),

    AUTHENTICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "K101", "인가 코드가 만료되었습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "S101", "서버 내부 오류입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U101", "해당 유저는 존재하지 않습니다."),
    ;

    int status;
    String code;
    String message;
}
