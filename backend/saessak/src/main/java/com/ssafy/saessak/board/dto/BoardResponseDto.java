package com.ssafy.saessak.board.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class BoardResponseDto {
    Long boardId;
    Long kidId;
    Date boardDate;
    String boardPath;
    // 썸네일
}
