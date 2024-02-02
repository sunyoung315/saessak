package com.ssafy.saessak.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MenuRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate menuDate;
    private String menuType;
    private String foodName;
    private String foodAllergy;

}
