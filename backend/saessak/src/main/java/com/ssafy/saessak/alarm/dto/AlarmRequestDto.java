package com.ssafy.saessak.alarm.dto;

import com.ssafy.saessak.fcm.dto.FcmResponseDto;
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

    public static AlarmRequestDto of (
            final Long kidId,
            final String alarmType,
            final LocalDate alarmDate,
            final LocalTime alarmContent
    ) {
        return AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(alarmType)
                .alarmDate(alarmDate)
                .alarmContent(alarmContent)
                .build();
    }

}
