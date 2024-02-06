package com.ssafy.saessak.board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private Long boardId;
    private Long kidId;
    private LocalDate boardDate;
    private String boardContent;
    private String boardTemperature;
    private Float boardSleepTime;
    private String boardPoopStatus;
    private Float boardTall;
    private Float boardWeight;
}
