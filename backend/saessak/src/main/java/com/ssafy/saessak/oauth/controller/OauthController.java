package com.ssafy.saessak.oauth.controller;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.RegistRequestDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.oauth.service.KakaoSocialService;
import com.ssafy.saessak.oauth.service.KakaoUserService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.oauth.service.TeacherService;
import com.ssafy.saessak.oauth.token.service.RefreshTokenService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    private final KakaoSocialService kakaoSocialService;
    private final RefreshTokenService refreshTokenService;
    private final KakaoUserService kakaoUserService;
    private final ParentService parentService;
    private final TeacherService teacherService;


    @Operation(summary = "카카오 로그인 창 접근")
    @GetMapping("/kakao/login")
    public String getKakaologin() {
        return kakaoSocialService.getKakaoLogin();
    }

    @Operation(summary = "사용자 검증 (로그인 or 회원가입)")
    @GetMapping("/kakao/callback/{code}")
    public ResponseEntity<ResultResponse> login(@PathVariable("code") String code) {
        KakaoUserResponse kakaoUserResponse = kakaoSocialService.login(code);
        if(kakaoUserService.checkUser(kakaoUserResponse)) {
            if(kakaoUserService.isParent(kakaoUserResponse).isPresent()) {
                System.out.println("부모 로그인");
                Parent parent = kakaoUserService.isParent(kakaoUserResponse).get();
                LoginSuccessResponseDto loginSuccessResponseDto = parentService.getTokenByUserId(parent.getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,parentService.login(loginSuccessResponseDto)));
            }
            if(kakaoUserService.isTeacher(kakaoUserResponse).isPresent()) {
                System.out.println("선생 로그인");
                Teacher teacher = kakaoUserService.isTeacher(kakaoUserResponse).get();
                LoginSuccessResponseDto loginSuccessResponseDto = teacherService.getTokenByUserId(teacher.getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,teacherService.login(loginSuccessResponseDto)));
            }
        } else {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoUserService.regist(code, kakaoUserResponse)));
        }
        return null;
    }

    @Operation(summary = "선생님/학부모 회원가입")
    @PostMapping("/kakao/join")
    public ResponseEntity<ResultResponse> join(@RequestBody RegistRequestDto registRequestDto) {
        System.out.println("/kakao/join");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoUserService.join(registRequestDto)));
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
    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(final Principal principal) {
        refreshTokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

}
