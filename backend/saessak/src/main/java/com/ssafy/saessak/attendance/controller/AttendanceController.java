package com.ssafy.saessak.attendance.controller;

import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.attendance.dto.AttendanceRequestDto;
import com.ssafy.saessak.attendance.dto.AttendanceTimeResponseDto;
import com.ssafy.saessak.attendance.dto.ReplacementResponseDto;
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
    private final AlarmService alarmService;

    @PostMapping(value = "/in/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> inTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto responseDto = attendanceService.inTime(kidId);
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
              .token(parentToken)
              .title("등원 알림")
              .body(responseDto.getKidName()+" 어린이가 "+ responseDto.getAttendanceDate()+"일 "+responseDto.getAttendanceTime()+"에 등원했습니다")
              .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
              .notification(notification)
              .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType("등원 알림")
                .alarmDate(responseDto.getAttendanceDate())
                .alarmContent(responseDto.getAttendanceTime())
                .build();

        fcmService.sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @PostMapping(value = "/out/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> outTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto attendanceTimeResponseDto = attendanceService.outTime(kidId);
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = null;
        FcmNotificationRequestDto fcmRequestDto = null;
        AlarmRequestDto alarmRequestDto = null;

        ReplacementResponseDto replacementResponseDto = attendanceService.checkReplacement(kidId);
        if(replacementResponseDto != null) { // 대리인 귀가인 경우
            notification = FcmNotificationRequestDto.Notification.builder()
                    .token(parentToken)
                    .title("대리인 알림")
                    .body(replacementResponseDto.getReplacementRelationship()+
                            "("+replacementResponseDto.getReplacementName()+") 님이 "+replacementResponseDto.getKidName()+" 어린이와 함께 귀가했습니다")
                    .build();
            fcmRequestDto = FcmNotificationRequestDto.builder()
                    .notification(notification)
                    .build();

            alarmRequestDto = AlarmRequestDto.builder()
                    .kidId(kidId)
                    .alarmType("대리인 알림")
                    .build();
        } else {
            notification = FcmNotificationRequestDto.Notification.builder()
                    .token(parentToken)
                    .title("하원 알림")
                    .body(attendanceTimeResponseDto.getKidName() + " 어린이가 " + attendanceTimeResponseDto.getAttendanceDate() + "일 " + attendanceTimeResponseDto.getAttendanceTime() + "에 하원했습니다")
                    .build();
            fcmRequestDto = FcmNotificationRequestDto.builder()
                    .notification(notification)
                    .build();

            alarmRequestDto = AlarmRequestDto.builder()
                    .kidId(kidId)
                    .alarmType("하원 알림")
                    .alarmDate(attendanceTimeResponseDto.getAttendanceDate())
                    .alarmContent(attendanceTimeResponseDto.getAttendanceTime())
                    .build();
        }

        fcmService.sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, attendanceTimeResponseDto));
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> list(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, attendanceService.list(attendanceRequestDto)));
    }
}
