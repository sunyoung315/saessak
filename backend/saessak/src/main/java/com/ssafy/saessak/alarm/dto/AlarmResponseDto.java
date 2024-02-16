package com.ssafy.saessak.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class AlarmResponseDto {

    private Long alarmId;
    private String kidName;
    private String alarmType;
    private LocalDate alarmDate;
    private LocalTime alarmContent;

}
