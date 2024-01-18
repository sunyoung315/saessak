package com.ssafy.saessak.attendance.controller;

import com.ssafy.saessak.attendance.dto.AttendanceRequestDto;
import com.ssafy.saessak.attendance.dto.AttendanceTimeResponseDto;
import com.ssafy.saessak.attendance.service.AttendanceService;
import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;
    private final FcmService fcmService;

    @PostMapping(value = "/in/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> inTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto responseDto = attendanceService.inTime(kidId);
        String parentToken = userService.getParentToken(kidId);

      FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
              .token(parentToken)
              .title("등원 알림")
              .body(responseDto.getAttendanceDate()+"일 "+responseDto.getAttendanceTime()+"에 등원했습니다")
              .build();
      FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
              .notification(notification)
              .build();

        fcmService.sendNotification(fcmRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @PostMapping(value = "/out/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> outTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto responseDto = attendanceService.outTime(kidId);
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
                .token(parentToken)
                .title("하원 알림")
                .body(responseDto.getAttendanceDate()+"일 "+responseDto.getAttendanceTime()+"에 하원했습니다")
                .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
                .notification(notification)
                .build();

        fcmService.sendNotification(fcmRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> list(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, attendanceService.list(attendanceRequestDto)));
    }
}
