package com.ssafy.saessak.document.controller;

import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.document.service.AllergyService;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/allergy")
@RequiredArgsConstructor
@RestController
public class AllergyController {

    private final AllergyService allergyService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "식품 알레르기 동의서 입력")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody AllergyRequestDto allergyRequestDto) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, allergyRequestDto.getKidId());
        allergyService.insert(allergyRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님 식품 알레르기 동의서 목록 조회")
    @GetMapping(value = "/list/{classroomId}")
    public ResponseEntity<ResultResponse> list(@PathVariable("classroomId") Long classroomId) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, classroomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, allergyService.list(classroomId)));
    }

    @Operation(summary = "식품 알레르기 동의서 세부 내용 조회")
    @GetMapping(value = "/{kidId}")
    public ResponseEntity<ResultResponse> detail(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, allergyService.detail(kidId)));
    }

    @Operation(summary = "식품 알레르기 동의서 확인 버튼")
    @GetMapping(value = "/check/{kidId}")
    public ResponseEntity<ResultResponse> changeCheck(@PathVariable("kidId") Long kidId) {
        allergyService.changeCheck(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
