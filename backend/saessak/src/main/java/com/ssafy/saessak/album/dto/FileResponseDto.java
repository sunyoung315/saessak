package com.ssafy.saessak.album.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FileResponseDto {
    private Long fileId;
    private String fileName;
    private String filePath;
}
