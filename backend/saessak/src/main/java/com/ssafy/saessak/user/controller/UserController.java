package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.dto.KidRegistRequestDto;
import com.ssafy.saessak.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @Operation(summary = "아이와 부모 매핑")
    @PostMapping(value = "/{parentId}/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> mapping(@PathVariable("parentId")Long parentId, @PathVariable("kidId")Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.mapping(parentId, kidId)));
    }

//    @Operation(summary = "반 아이 목록 조회")
//    @GetMapping(value = "/kid/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResultResponse> getClassKid(@PathVariable("classroomId") Long classroomId) {
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getClassKid(classroomId)));
//    }
//
//    @Operation(summary = "학부모가 자신의 선생님 목록 확인")
//    @GetMapping(value = "/teacher/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResultResponse> getParentTeacher(@PathVariable("parentId") Long parentId) {
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getParentTeacher(parentId)));
//    }
}
