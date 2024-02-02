package com.ssafy.saessak.attendance.controller;

import com.ssafy.saessak.attendance.dto.AttendanceRequestDto;
import com.ssafy.saessak.attendance.dto.AttendanceTimeResponseDto;
import com.ssafy.saessak.attendance.dto.ReplacementResponseDto;
import com.ssafy.saessak.attendance.service.AttendanceService;
import com.ssafy.saessak.document.service.ReplacementService;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final FcmService fcmService;
    private final ReplacementService replacementService;

    @Operation(summary = "등원 여부 입력")
    @PostMapping(value = "/in/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> inTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto responseDto = attendanceService.inTime(kidId);
        fcmService.sendInTime(responseDto, kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @Operation(summary = "하원 여부 입력")
    @PostMapping(value = "/out/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> outTime(@PathVariable("kidId") Long kidId) {
        AttendanceTimeResponseDto responseDto = attendanceService.outTime(kidId);
        ReplacementResponseDto replacementResponseDto = replacementService.checkReplacement(kidId);

        if(replacementResponseDto != null) { // 대리인 귀가인 경우
            fcmService.sendReplacement(replacementResponseDto, kidId);
        } else {
            fcmService.sendOutTime(responseDto, kidId);
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, responseDto));
    }

    @Operation(summary = "주간 출석부 조회(선생님)")
    @PostMapping(value = "/teacher/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> listOfteacher(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, attendanceService.listOfteacher(attendanceRequestDto)));
    }

    @Operation(summary = "아이 출석부 조회(학부모)")
    @GetMapping(value = "/parent/list/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> listOfParent(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, attendanceService.listOfParent(kidId)));
    }

}
