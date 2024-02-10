package com.ssafy.saessak.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class KidNoBoardResponseDto {
    private Long kidId;
    private String kidName;
}
