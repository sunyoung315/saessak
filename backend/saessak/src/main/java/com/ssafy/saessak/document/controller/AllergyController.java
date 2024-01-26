package com.ssafy.saessak.document.controller;

import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.document.service.AllergyService;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.user.domain.User;
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

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody AllergyRequestDto allergyRequestDto) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, allergyRequestDto.getKidId());
        allergyService.insert(allergyRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @GetMapping(value = "/list/{classroomId}")
    public ResponseEntity<ResultResponse> list(@PathVariable("classroomId") Long classroomId) {
        User user = authenticationService.getUserByAuthentication();
        authenticationService.AuthenticationByObject(user, classroomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, allergyService.list(classroomId)));
    }

    @GetMapping(value = "/{kidId}")
    public ResponseEntity<ResultResponse> detail(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, allergyService.detail(kidId)));
    }

    @GetMapping(value = "/check/{kidId}")
    public ResponseEntity<ResultResponse> changeCheck(@PathVariable("kidId") Long kidId) {
        allergyService.changeCheck(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
