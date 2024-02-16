package com.ssafy.saessak.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class AttendanceKidListResponseDto {

    private Long kidId;
    private String kidName;
    private List<AttendanceKidResponseDto> attendance;
}
