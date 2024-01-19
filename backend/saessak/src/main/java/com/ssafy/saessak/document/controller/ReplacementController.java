package com.ssafy.saessak.document.controller;

import com.ssafy.saessak.document.dto.ReplacementRequestDto;
import com.ssafy.saessak.document.service.ReplacementService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/document/replacement")
@RequiredArgsConstructor
@RestController
public class ReplacementController {

    private final ReplacementService replacementService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody ReplacementRequestDto replacementRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, replacementService.insert(replacementRequestDto)));
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
