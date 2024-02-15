package com.ssafy.saessak.exception.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ExceptionDto {

    private final int status;
    private final String code;
    private final String message;

    public static ResponseEntity<ExceptionDto> toResponseEntity(int status, String code, String message){

        return ResponseEntity
                .status(status)
                .body(ExceptionDto.builder()
                        .code(code)
                        .message(message)
                        .build());
    }
}
