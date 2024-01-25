package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;


@Getter
@Builder
public class ContentResponseDto {
    Date boardDate;
    String boardContent;
}
