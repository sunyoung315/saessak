package com.ssafy.saessak.alarm.controller;

import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.User;
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

    @GetMapping(value = "/kid/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> kidAlarmList(@PathVariable("kidId") Long kidId) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, alarmService.kidAlarmList(kidId)));
    }

    @GetMapping(value = "/classroom/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> classroomAlarmList(@PathVariable("classroomId") Long classroomId) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, classroomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, alarmService.classroomAlarmList(classroomId)));
    }

    @DeleteMapping(value = "/{alarmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> delete(@PathVariable("alarmId") Long alarmId) {
        alarmService.delete(alarmId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteAll() {
        alarmService.deleteAll();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
