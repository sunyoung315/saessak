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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/allergy")
@RequiredArgsConstructor
@RestController
public class AllergyController {

    private final AllergyService allergyService;

    @Operation(summary = "식품 알레르기 동의서 입력")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody AllergyRequestDto allergyRequestDto) {
        allergyService.insert(allergyRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "식품 알레르기 동의서 전자서명 입력")
    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse> insertSign(@RequestParam("kidId") Long kidId,
                                                     @RequestParam("signFile") MultipartFile signFile) throws IOException {

        allergyService.insertSign(kidId, signFile);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님 식품 알레르기 동의서 목록 조회")
    @GetMapping(value = "/list")
    public ResponseEntity<ResultResponse> list() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, allergyService.list()));
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
