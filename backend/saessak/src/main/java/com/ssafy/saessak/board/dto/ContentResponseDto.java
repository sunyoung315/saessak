package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Builder
public class ContentResponseDto {
    private LocalDate boardDate;
    private String boardContent;
}
