package com.ssafy.saessak.board.dto;

import com.ssafy.saessak.user.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class PhysicalResponseDto {
    private Long kidId;
    private Gender gender;
    private LocalDate kidBirthday;
    private String kidName;
    private List<PhysicalDto> physicalDtoList;

}
