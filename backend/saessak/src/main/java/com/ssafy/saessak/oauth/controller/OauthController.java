package com.ssafy.saessak.oauth.controller;

import com.ssafy.saessak.oauth.dto.AccessTokenGetSuccess;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.RegistRequestDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.oauth.service.KakaoSocialService;
import com.ssafy.saessak.oauth.service.KakaoUserService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.oauth.service.TeacherService;
import com.ssafy.saessak.jwt.refreshToken.service.RefreshTokenService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

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
    
    @Operation(summary = "백엔드 로그인")
    @GetMapping("/kakao/callback/backend")
    public ResponseEntity<ResultResponse> backendLogin(HttpServletRequest request) {
        KakaoUserResponse kakaoUserResponse = kakaoSocialService.login(request.getParameter("code"));
        if(kakaoUserService.checkUser(kakaoUserResponse)) {

            Optional<Parent> parent = parentService.isParent(kakaoUserResponse);
            if(parent.isPresent()){
                LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(parent.get().getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,parentService.login(loginSuccessResponseDto)));
            }

            Optional<Teacher> teacher = teacherService.isTeacher(kakaoUserResponse);
            if(teacher.isPresent()) {
                LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(teacher.get().getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,teacherService.login(loginSuccessResponseDto)));
            }
        } else {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoUserService.registUser(kakaoUserResponse)));
        }
        return null;
    }

    @Operation(summary = "사용자 검증 (로그인 or 회원가입)")
    @GetMapping("/kakao/callback/{code}")
    public ResponseEntity<ResultResponse> login(@PathVariable("code") String code) {
        KakaoUserResponse kakaoUserResponse = kakaoSocialService.getUserInfo(code);
        if(kakaoUserService.checkUser(kakaoUserResponse)) {

            Optional<Parent> parent = parentService.isParent(kakaoUserResponse);
            if(parent.isPresent()){
                LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(parent.get().getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,parentService.login(loginSuccessResponseDto)));
            }

            Optional<Teacher> teacher = teacherService.isTeacher(kakaoUserResponse);
            if(teacher.isPresent()) {
                LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(teacher.get().getId());
                return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,teacherService.login(loginSuccessResponseDto)));
            }
        } else {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoUserService.registUser(kakaoUserResponse)));
        }
        return null;
    }

    @Operation(summary = "선생님/학부모 회원가입")
    @PostMapping("/kakao/join")
    public ResponseEntity<ResultResponse> join(@RequestBody RegistRequestDto registRequestDto) {
        String registCode = new String(Base64.getDecoder().decode(registRequestDto.getRegistCode()), StandardCharsets.UTF_8);

        if(registCode.substring(registCode.length()-3, registCode.length()).equals("kid")) {
            Long parentId = parentService.registParent(registRequestDto.getUserId());
            Long mapping_kidId = Long.parseLong(registCode.substring(0, registCode.length()-3));
            parentService.mapping(parentId, mapping_kidId);
            LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(parentId);
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, parentService.login(loginSuccessResponseDto)));
        }

        else if(registCode.substring(registCode.length()-9, registCode.length()).equals("classroom")) {
            Long teacherId = teacherService.registTeacher(registRequestDto.getUserId());
            Long mapping_classroomId = Long.parseLong(registCode.substring(0, registCode.length()-9));
            teacherService.mapping(teacherId, mapping_classroomId);
            LoginSuccessResponseDto loginSuccessResponseDto = kakaoUserService.getTokenByUserId(teacherId);
            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, teacherService.login(loginSuccessResponseDto)));
        }

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "refreshToken으로 accessToken 재발급")
    @GetMapping("/refreshtoken")
    public ResponseEntity<ResultResponse> refreshToken(@RequestParam final String refreshToken) {
        AccessTokenGetSuccess accessTokenGetSuccess = kakaoUserService.refreshToken(refreshToken);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoUserService.refreshToken(refreshToken)));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(final Principal principal) {
        refreshTokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

}
