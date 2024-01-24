package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.dto.KidRegistRequestDto;
import com.ssafy.saessak.user.dto.ParentRegistRequestDto;
import com.ssafy.saessak.user.service.KakaoService;
import com.ssafy.saessak.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping(value = "/kid/register/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registKid(@PathVariable("classroomId") Long classroomId, @RequestBody KidRegistRequestDto kidRegistRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registKid(classroomId, kidRegistRequestDto)));
    }

    @PostMapping(value = "/{parentId}/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> mapping(@PathVariable("parentId")Long parentId, @PathVariable("kidId")Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.mapping(parentId, kidId)));
    }

    @GetMapping(value = "/kid/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getClassKid(@PathVariable("classroomId") Long classroomId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getClassKid(classroomId)));
    }

//    @GetMapping(value = "/parent/login")
//    public ResponseEntity<ResultResponse> parentLogin(HttpServletResponse response) throws IOException {
//        String kakao_uri = kakaoService.getKakaoLogin();
//        response.sendRedirect(kakao_uri);
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
//    }
}
