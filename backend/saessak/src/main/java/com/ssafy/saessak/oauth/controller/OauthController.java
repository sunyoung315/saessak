package com.ssafy.saessak.oauth.controller;

import com.ssafy.saessak.oauth.service.KakaoSocialService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OauthController {

    private final KakaoSocialService kakaoSocialService;
    private final ParentService parentService;


    @GetMapping("/kakao/login")
    public ResponseEntity<ResultResponse> login(HttpServletResponse response) throws IOException {
        String kakao_uri = kakaoSocialService.getKakaoLogin();
        response.sendRedirect(kakao_uri);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<ResultResponse> login(HttpServletRequest request) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoSocialService.login(request.getParameter("code"))));
    }

//    @GetMapping("/kakao/callback")
//    public ResponseEntity<ResultResponse> login(@RequestParam final String authorizationCode) {
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kakaoSocialService.login(authorizationCode)));
//    }

//    @GetMapping("/token-refresh")
//    public SuccessResponse<AccessTokenGetSuccess> refreshToken(
//            @RequestParam final String refreshToken
//    ) {
//        return SuccessResponse.of(SuccessMessage.ISSUE_ACCESS_TOKEN_SUCCESS, userService.refreshToken(refreshToken));
//    }
//
//    @DeleteMapping("/delete")
//    public SuccessResponse deleteUser(
//            final Principal principal
//    ) {
//        tokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
//        userService.deleteUser(Long.valueOf(principal.getName()));
//        return SuccessResponse.of(SuccessMessage.USER_DELETE_SUCCESS);
//    }
//
//    @PostMapping("/logout")
//    public SuccessResponse logout(
//            final Principal principal
//    ) {
//        tokenService.deleteRefreshToken(Long.valueOf(principal.getName()));
//        return SuccessResponse.of(SuccessMessage.LOGOUT_SUCCESS);
//    }

}
