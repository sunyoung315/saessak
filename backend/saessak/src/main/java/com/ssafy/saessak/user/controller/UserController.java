package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.Gender;
import com.ssafy.saessak.user.dto.KidMappingRequestDto;
import com.ssafy.saessak.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @Operation(summary = "아이와 부모 매핑")
    @PostMapping(value = "/kid/mapping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> mapping(@RequestBody KidMappingRequestDto mappingRequestDto) {
        userService.mapping(mappingRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님이 반아이 등록")
    @PostMapping(value = "/kid/regist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse> registKid(@RequestParam Gender gender, @RequestParam String kidName, @RequestParam LocalDate kidBirthday, @RequestParam("MultipartFile") MultipartFile kidProfile) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registKid(gender, kidName, kidBirthday, kidProfile)));
    }

    @Operation(summary = "반 아이 목록 조회")
    @GetMapping(value = "/kid/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getClassKid() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getClassKid()));
    }

    @Operation(summary = "학부모가 자신의 선생님 목록 확인")
    @GetMapping(value = "/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getParentTeacher() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getParentTeacher()));
    }

    @Operation(summary = "학부모 아이 전체 목록 조회")
    @GetMapping(value = "/parent/kidlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getParentKid() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getParentKid()));
    }
}
