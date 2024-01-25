package com.ssafy.saessak.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuWeekRequestDto {

    private Long daycardId;
    private int year;
    private int month;
    private int week;

}
