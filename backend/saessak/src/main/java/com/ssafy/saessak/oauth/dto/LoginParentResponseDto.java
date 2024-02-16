package com.ssafy.saessak.oauth.dto;

import com.ssafy.saessak.fcm.dto.FcmResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class LoginParentResponseDto {

    private boolean isTeacher;
    private boolean isAlarm;
    private String accessToken;
    private String refreshToken;
    private List<KidResponseDto> kidList;

}
