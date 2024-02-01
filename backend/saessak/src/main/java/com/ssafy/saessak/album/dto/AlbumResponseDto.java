package com.ssafy.saessak.album.dto;


import com.ssafy.saessak.album.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class AlbumResponseDto {
    private Long albumId;
    private LocalDate albumDate;
    private String albumTitle;
    private List<FileResponseDto> fileResponseDtoList;
}
