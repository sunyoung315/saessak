package com.ssafy.saessak.album.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Getter
@NoArgsConstructor
public class AlbumRequestDto {

    private Long albumId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate albumDate;
}
