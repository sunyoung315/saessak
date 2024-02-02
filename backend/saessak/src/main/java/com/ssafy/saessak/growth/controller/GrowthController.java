package com.ssafy.saessak.growth.controller;

import com.ssafy.saessak.growth.dto.GrowthRequestDto;
import com.ssafy.saessak.growth.dto.GrowthResponseDto;
import com.ssafy.saessak.growth.service.GrowthService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/growth")
public class GrowthController {

    private final GrowthService growthService;

    @PostMapping("")
    public ResponseEntity<ResultResponse> getAvgGrowthInfo(@RequestBody GrowthRequestDto growthRequestDto){
        GrowthResponseDto growthResponseDto =
                growthService.getAvgGrowthInfo(growthRequestDto.getGender(),
                        growthRequestDto.getStartMonth(),
                        growthRequestDto.getEndMonth()
                );

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, growthResponseDto));
    }
}
