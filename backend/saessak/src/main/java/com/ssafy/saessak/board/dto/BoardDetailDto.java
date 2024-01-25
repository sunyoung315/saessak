package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;


@Builder
@Getter
public class BoardDetailDto {
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
    String boardPath;
}
