package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.dto.KakaoResponseDto;
import com.ssafy.saessak.user.service.KakaoService;
import com.ssafy.saessak.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

//    @GetMapping("/callback")
//    public ResponseEntity<ResultResponse> callback(HttpServletRequest request) throws Exception {
//        KakaoResponseDto kakaoResponseDto = kakaoService.getKakaoInfo(request.getParameter("code"));
////        String email = userService.parentLogin(kakaoResponseDto);
//
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoResponseDto));
//    }

}
