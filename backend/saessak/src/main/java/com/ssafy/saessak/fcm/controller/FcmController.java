package com.ssafy.saessak.fcm.controller;

import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    @Autowired
    // 추후 작성 예정
    private FcmService fcmService;

    @PostMapping("/parent/token")
    public ResponseEntity<?> saveParentToken(@RequestBody FcmTokenRequestDto requestDto) {
        fcmService.saveParentToken(requestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

//    @PostMapping("/teacher/token")
//    public ResponseEntity<?> saveTeacherToken(@RequestBody FcmTokenRequestDto requestDto) {
//        fcmService.saveTeacherToken(requestDto);
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
//    }

    @GetMapping("/parent/alarm/{parentId}")
    public ResponseEntity<?> changeParentAlarm(@PathVariable("parentId") Long parentId) {
        fcmService.changeParentAlarm(parentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

//    @GetMapping("/parent/alarm/{teacherId}")
//    public ResponseEntity<?> changeParentAlarm(@PathVariable("teacherId") Long teacherId) {
//        fcmService.changeTeacherAlarm(teacherId);
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
//    }

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