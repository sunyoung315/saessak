package com.ssafy.saessak.alarm.controller;

import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "모든 알림 조회(학부모)")
    @GetMapping(value = "/kid/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> kidAlarmList(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, alarmService.alarmListOfParent(kidId)));
    }

    @Operation(summary = "모든 알림 조회(선생님)")
    @GetMapping(value = "/classroom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> classroomAlarmList() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, alarmService.alarmListOfTeacher()));
    }

    @Operation(summary = "알림 삭제")
    @DeleteMapping(value = "/{alarmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> delete(@PathVariable("alarmId") Long alarmId) {
        alarmService.delete(alarmId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "알림 전체 삭제 (학부모)")
    @DeleteMapping(value = "/kid/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteAllofParent(@PathVariable("kidId") Long kidId) {
        alarmService.deleteAllofParent(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "알림 전체 삭제 (선생님)")
    @DeleteMapping(value = "/classroom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteAllofTeacher() {
        alarmService.deleteAllofTeacher();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
