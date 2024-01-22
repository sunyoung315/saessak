package com.ssafy.saessak.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MenuRequestDto {

    private LocalDate menuDate;
    private String menuType;
    private String foodName;
    private String foodAllergy;

}
