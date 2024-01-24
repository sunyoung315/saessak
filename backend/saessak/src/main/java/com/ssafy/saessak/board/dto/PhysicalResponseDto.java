package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Builder
public class PhysicalResponseDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date boardDate;
    Float boardTall;
    Float boardWeight;
}
