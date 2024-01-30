package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.dto.KidRegistRequestDto;
import com.ssafy.saessak.user.dto.KidMappingRequestDto;
import com.ssafy.saessak.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final ParentService parentService;


    @Operation(summary = "아이와 부모 매핑")
    @PostMapping(value = "/kid/mapping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> mapping(@RequestBody KidMappingRequestDto mappingRequestDto) {
        userService.mapping(mappingRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님이 반아이 등록")
    @PostMapping(value = "/kid/regist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registKid(@RequestBody KidRegistRequestDto requestDto, @RequestPart("MultipartFile") MultipartFile kidProfile) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registKid(requestDto, kidProfile)));
    }

    @Operation(summary = "반 아이 목록 조회")
    @GetMapping(value = "/kid/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getClassKid() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getClassKid()));
    }

//    @Operation(summary = "학부모가 자신의 선생님 목록 확인")
//    @GetMapping(value = "/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResultResponse> getParentTeacher() {
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getParentTeacher()));
//    }
}
