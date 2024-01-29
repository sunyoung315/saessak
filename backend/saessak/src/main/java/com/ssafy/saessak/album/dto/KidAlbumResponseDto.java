package com.ssafy.saessak.album.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KidAlbumResponseDto {
    private Long kidId;
    private String kidName;
    AlbumResponseDto albumResponseDto;
}
