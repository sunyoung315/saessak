package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Builder
public class PhysicalDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate boardDate;
    private Float boardTall;
    private Float boardWeight;
}
