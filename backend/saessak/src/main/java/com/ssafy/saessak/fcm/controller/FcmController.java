package com.ssafy.saessak.fcm.controller;

import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/fcm")
@RequiredArgsConstructor
@RestController
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "알림기기 토큰 갱신 (학부모, 선생님)")
    @PostMapping("/token")
    public ResponseEntity<?> saveToken(@RequestBody FcmTokenRequestDto requestDto) {
        fcmService.saveToken(requestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "알림 활성화 여부 변경 (학부모, 선생님)")
    @GetMapping("/parent/alarm/{parentId}")
    public ResponseEntity<?> changeAlarm() {
        fcmService.changeAlarm();
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