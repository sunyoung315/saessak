package com.ssafy.saessak.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class AlarmRequestDto {

    private Long kidId;
    private String alarmType;
    private LocalDate alarmDate;
    private LocalTime alarmContent;

}
