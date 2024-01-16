package com.ssafy.saessak.user.controller;

import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.dto.KidRegistRequestDto;
import com.ssafy.saessak.user.dto.ParentRegistRequestDto;
import com.ssafy.saessak.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/parent/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registParent(@RequestBody ParentRegistRequestDto parentRegistRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registParent(parentRegistRequestDto)));
    }

    @PostMapping(value = "/kid/register/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registKid(@PathVariable("classroomId") Long classroomId, @RequestBody KidRegistRequestDto kidRegistRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.registKid(classroomId, kidRegistRequestDto)));
    }

    @PostMapping(value = "/{parentId}/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> mapping(@PathVariable("parentId")Long parentId, @PathVariable("kidId")Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.mapping(parentId, kidId)));
    }

    @GetMapping(value = "/kid/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getClassKid(@PathVariable("classroomId") Long classroomId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userService.getClassKid(classroomId)));
    }
}
