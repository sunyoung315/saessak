package com.ssafy.saessak.board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    Long boardId;
    Long kidId;
    Long classroomId;
    Date boardDate;
    String boardContent;
    String boardTemperature;
    Integer boardSleepTime;
    String boardPoopStatus;
    Float boardTall;
    Float boardWeight;
}
