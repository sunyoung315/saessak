package com.ssafy.saessak.oauth.controller;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.service.KakaoSocialService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.oauth.service.TeacherService;
import com.ssafy.saessak.oauth.token.service.RefreshTokenService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    private final KakaoSocialService kakaoSocialService;
    private final ParentService parentService;
    private final TeacherService teacherService;
    private final RefreshTokenService refreshTokenService;


    @GetMapping("/kakao/login")
    public String login(HttpServletResponse response) throws IOException {
        String kakao_uri = kakaoSocialService.getKakaoLogin();
//        response.sendRedirect(kakao_uri);
        return kakao_uri;
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<ResultResponse> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginSuccessResponseDto loginSuccessResponseDto = kakaoSocialService.login(request.getParameter("code"));
        if(loginSuccessResponseDto.isTeacher()) {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, teacherService.login(loginSuccessResponseDto)));
        } else {
            parentService.login(loginSuccessResponseDto);
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, parentService.login(loginSuccessResponseDto)));
        }
    }

//    @GetMapping("/token-refresh")
//    public ResponseEntity<ResultResponse> refreshToken(@RequestParam final String refreshToken) {
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, parentService.refreshToken(refreshToken)));
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<ResultResponse> deleteUser(final Principal principal) {
//        refreshTokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
//        parentService.deleteUser(Long.valueOf(principal.getName()));
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
//    }
//
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(final Principal principal) {
        refreshTokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

}
