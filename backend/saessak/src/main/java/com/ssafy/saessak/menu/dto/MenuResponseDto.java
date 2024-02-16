package com.ssafy.saessak.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MenuResponseDto {

    private Long menuId;
    private LocalDate menuDate;
    private String menuType;
    private String menuPath;
    private List<FoodResponseDto> foodList;

}
