package com.ssafy.saessak.growth.dto;

import com.ssafy.saessak.user.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRequestDto {
    private Gender gender;
    private Integer startMonth;
    private Integer endMonth;
}
