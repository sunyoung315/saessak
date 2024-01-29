package com.ssafy.saessak.fcm.controller;

import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

@RequestMapping("/api/fcm")
@RequiredArgsConstructor
@RestController
public class FcmController {

    private final FcmService fcmService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "학부모 알림기기 토큰 갱신")
    @PostMapping("/parent/token")
    public ResponseEntity<?> saveParentToken(@RequestBody FcmTokenRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationById(user, requestDto.getId());
        fcmService.saveParentToken(requestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님 알림기기 토큰 갱신")
    @PostMapping("/teacher/token")
    public ResponseEntity<?> saveTeacherToken(@RequestBody FcmTokenRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationById(user, requestDto.getId());
        fcmService.saveTeacherToken(requestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "학부모 알림 활성화 여부 변경")
    @GetMapping("/parent/alarm/{parentId}")
    public ResponseEntity<?> changeParentAlarm(@PathVariable("parentId") Long parentId) {
        User user = authenticationService.getUserByAuthentication();
        System.out.println("인증 user id "+user.getId());
        authenticationService.AuthenticationById(user, parentId);
        fcmService.changeParentAlarm(parentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님 알림 활성화 여부 변경")
    @GetMapping("/teacher/alarm/{teacherId}")
    public ResponseEntity<?> changeTeacherAlarm(@PathVariable("teacherId") Long teacherId) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationById(user, teacherId);
        fcmService.changeTeacherAlarm(teacherId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

//    @PostMapping("/sendNotification")
//    // DTO 작성 예정
//    public ResponseEntity<?> sendNotification(@Valid @RequestBody FcmNotificationRequestDto dto){
//
//        // 테스트로 한 번 찍어본다.
//        System.out.println("테스트 : "+dto.getNotification().getToken());
//        System.out.println("테스트 : "+dto.getNotification().getTitle());
//        System.out.println("테스트 : "+dto.getNotification().getBody());
//
//        // 추후 작성 예정인 fcmServiceApiV1의 sendNotification 함수를 호출하여 그대로 반환한다.
//        return fcmService.sendNotification(dto);
//    }

}