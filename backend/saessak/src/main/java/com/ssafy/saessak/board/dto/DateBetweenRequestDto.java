package com.ssafy.saessak.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@NoArgsConstructor
public class DateBetweenRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;

}
