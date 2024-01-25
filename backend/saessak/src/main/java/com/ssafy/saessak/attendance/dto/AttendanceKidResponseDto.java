package com.ssafy.saessak.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class AttendanceKidResponseDto {

    private Long attendanceId;
    private LocalDate attendanceDate;
    private LocalTime attendanceInTime;
    private LocalTime attendanceOutTime;
}
