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
    REFRESH_TOKEN_USER_MISMATCH(HttpStatus.UNAUTHORIZED.value(), "R103", "해당 유저의 리프레시 토큰과 일치하지 않습니다"),
    AUTHENTICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST.value(), "A201", "인가 코드가 만료되었습니다."),
    AUTHENTICATION_USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "A202", "인증한 유저가 존재하지 않습니다"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U101", "해당 유저는 존재하지 않습니다."),
    KID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U201", "해당 아이는 존재하지 않습니다"),
    FAIL_KID_PROFILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR.value(), "U202", "아이 프로필 사진 업로드를 실패했습니다"),
    PARENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U301", "해당 학부모는 존재하지 않습니다"),
    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U401", "해당 선생님은 존재하지 않습니다"),
    CLASSROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U501", "해당 반은 존재하지 않습니다"),
    DAYCARE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U601", "해당 어린이집은 존재하지 않습니다"),

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M101", "해당 메뉴는 존재하지 않습니다"),
    MENU_DATE_AND_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M101", "오늘 날짜의 해당 타입의 메뉴는 존재하지 않습니다"),
    REPLACEMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R201", "대리인 귀가동의서가 존재하지 않습니다"),
    ATTENDANCE_KID_AND_DATE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R201", "아이의 등원 기록이 없습니다"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "B101", "해당 알림장은 존재하지 않습니다."),


    FAIL_FCM_ALARM(HttpStatus.INTERNAL_SERVER_ERROR.value(), "F601", "FCM 알람 전송에 실패했습니다."),
    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "F602", "FCM 토큰이 존재하지 않습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "S101", "서버 내부 오류입니다."),
    ;

    int status;
    String code;
    String message;
}
