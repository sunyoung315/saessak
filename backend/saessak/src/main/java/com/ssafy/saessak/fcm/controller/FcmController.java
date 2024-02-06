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

    @Operation(summary = "알림기기 토큰 삭제 (학부모, 선생님)")
    @DeleteMapping("/token")
    public ResponseEntity<?> deleteToken() {
        fcmService.deleteToken();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

}