package com.ssafy.saessak.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FoodResponseDto {

    private Long foodId;
    private String foodName;
    private String allergyNum;

}
