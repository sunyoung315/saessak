package com.ssafy.saessak.menu.controller;

import com.ssafy.saessak.menu.dto.MenuRequestDto;
import com.ssafy.saessak.menu.dto.MenuWeekRequestDto;
import com.ssafy.saessak.menu.service.MenuService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/menu")
@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "자유로운 식단표 입력")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody List<MenuRequestDto> menuRequestDtoList) {
        menuService.insert(menuRequestDtoList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "주간 식단표 조회 (선생님)")
    @PostMapping(value = "/teacher/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> listofTeacher(@RequestBody MenuWeekRequestDto menuWeekRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, menuService.listofTeacher(menuWeekRequestDto)));
    }

    @Operation(summary = "주간 식단표 조회 (학부모)")
    @PostMapping(value = "/parent/list/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> listofParent(@PathVariable("kidId") Long kidId, @RequestBody MenuWeekRequestDto menuWeekRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, menuService.listofParent(kidId, menuWeekRequestDto)));
    }

    @Operation(summary = "식단 메인 사진 추가")
    @PostMapping(value = "/photo/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse> insertPhoto(@PathVariable("menuId") Long menuId,
                                                      @RequestPart("MultipartFile") MultipartFile menuFile) {
        try {
            menuService.insertPhoto(menuId, menuFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
