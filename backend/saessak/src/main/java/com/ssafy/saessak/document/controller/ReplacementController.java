package com.ssafy.saessak.document.controller;

import com.ssafy.saessak.document.dto.ReplacementRequestDto;
import com.ssafy.saessak.document.service.ReplacementService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/document/replacement")
@RequiredArgsConstructor
@RestController
public class ReplacementController {

    private final ReplacementService replacementService;

    @Operation(summary = "대리인 귀가 동의서 입력")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody ReplacementRequestDto replacementRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.insert(replacementRequestDto)));
    }

    @Operation(summary = "대리인 귀가 동의서 전자서명 입력")
    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse> insertSign(@RequestParam("replacementId") Long replacementId,
                                                      @RequestParam("signFile") MultipartFile signFile) throws IOException {

        replacementService.insertSign(replacementId, signFile);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "대리인 귀가 동의서 목록 조회(학부모)")
    @GetMapping(value = "/list/kid/{kidId}")
    public ResponseEntity<ResultResponse> listOfkidId(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.listOfkidId(kidId)));
    }

    @Operation(summary = "대리인 귀가 동의서 목록 조회(선생님)")
    @GetMapping(value = "/list/classroom")
    public ResponseEntity<ResultResponse> listOfclassroomId() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.listOfclassroomId()));
    }

    @Operation(summary = "대리인 귀가 동의서 세부내용 조회")
    @GetMapping(value = "/{replacementId}")
    public ResponseEntity<ResultResponse> detail(@PathVariable("replacementId") Long replacementId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.detail(replacementId)));
    }

    @Operation(summary = "대리인 귀가 동의서 확인 버튼")
    @GetMapping(value = "/check/{replacementId}")
    public ResponseEntity<ResultResponse> changeCheck(@PathVariable("replacementId") Long replacementId) {
        replacementService.changeCheck(replacementId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
