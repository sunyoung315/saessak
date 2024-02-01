package com.ssafy.saessak.board.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
public class BoardResponseDto {
    private Long boardId;
    private Long kidId;
    private LocalDate boardDate;
    private String boardPath;
    // 썸네일
}
