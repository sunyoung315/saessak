package com.ssafy.saessak.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Getter
@NoArgsConstructor
public class DateRequestDto {
    private Long boardId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate boardDate;
}
