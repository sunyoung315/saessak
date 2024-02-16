package com.ssafy.saessak.growth.service;

import com.ssafy.saessak.growth.domain.KidTall;
import com.ssafy.saessak.growth.domain.KidWeight;
import com.ssafy.saessak.growth.dto.GrowthResponseDto;
import com.ssafy.saessak.growth.repository.KidTallRepository;
import com.ssafy.saessak.growth.repository.KidWeightRepository;
import com.ssafy.saessak.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrowthService {
    private final KidTallRepository kidTallRepository;
    private final KidWeightRepository kidWeightRepository;

    public GrowthResponseDto getAvgGrowthInfo (Gender gender, Integer startMonth, Integer endMonth){
        List< KidWeight> weights =
                kidWeightRepository.findAllByGenderAndNumberOfMonthBetween(gender,startMonth,endMonth).get();
        List< KidTall> talls =
                kidTallRepository.findAllByGenderAndNumberOfMonthBetween(gender, startMonth,endMonth).get();

        return GrowthResponseDto.builder()
                .weightList(weights)
                .tallList(talls)
                .build();
    }

}
