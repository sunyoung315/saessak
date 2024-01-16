package com.ssafy.saessak.user.dto;

import com.ssafy.saessak.user.domain.Parent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class KidRegistRequestDto {

    private String kidName;
    private LocalDate kidBirthday;

//    private String kidProfile; // s3 url 예정

}
