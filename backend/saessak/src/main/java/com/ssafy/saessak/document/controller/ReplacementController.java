package com.ssafy.saessak.document.controller;

import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.document.dto.ReplacementAlarmResponseDto;
import com.ssafy.saessak.document.dto.ReplacementRequestDto;
import com.ssafy.saessak.document.service.ReplacementService;
import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api/document/replacement")
@RequiredArgsConstructor
@RestController
public class ReplacementController {

    private final ReplacementService replacementService;
    private final FcmService fcmService;
    private final AlarmService alarmService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody ReplacementRequestDto replacementRequestDto) {
        ReplacementAlarmResponseDto responseDto = replacementService.insert(replacementRequestDto);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
                .token(responseDto.getTeacherAlarmDevice())
                .title("귀가동의서 알림")
                .body(responseDto.getKidName()+" 어린이의 대리인 귀가 동의서가 등록되었습니다")
                .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
                .notification(notification)
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(responseDto.getKidId())
                .alarmType("귀가동의서 알림")
                .alarmDate(LocalDate.now())
                .build();

        fcmService.sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @GetMapping(value = "/list/kid/{kidId}")
    public ResponseEntity<ResultResponse> listOfkidId(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.listOfkidId(kidId)));
    }

    @GetMapping(value = "/list/classroom/{classroomId}")
    public ResponseEntity<ResultResponse> listOfclassroomId(@PathVariable("classroomId") Long classroomId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.listOfclassroomId(classroomId)));
    }

    @GetMapping(value = "/{replacementId}")
    public ResponseEntity<ResultResponse> detail(@PathVariable("replacementId") Long replacementId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.detail(replacementId)));
    }


    @GetMapping(value = "/check/{replacementId}")
    public ResponseEntity<ResultResponse> changeCheck(@PathVariable("replacementId") Long replacementId) {
        replacementService.changeCheck(replacementId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
