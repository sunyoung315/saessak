package com.ssafy.saessak.growth.dto;

import com.ssafy.saessak.growth.domain.KidTall;
import com.ssafy.saessak.growth.domain.KidWeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrowthResponseDto {

    private List<KidWeight> weightList;
    private List<KidTall> tallList;
}
