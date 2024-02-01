package com.ssafy.saessak.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    SUCCESS(200, "정상 처리 되었습니다"),
    FAIL_AUTHENTICATION(401, "유효한 사용자가 아닙니다");

    private final int status;
    private final String message;

}
