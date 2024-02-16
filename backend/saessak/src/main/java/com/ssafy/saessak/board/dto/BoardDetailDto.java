package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;


@Builder
@Getter
public class BoardDetailDto {
    private Long boardId;
    private Long kidId;
    private Long classroomId;
    private LocalDate boardDate;
    private String boardContent;
    private Float boardTemperature;
    private Float boardSleepTime;
    private String boardPoopStatus;
    private Float boardTall;
    private Float boardWeight;
    private String boardPath;
}
