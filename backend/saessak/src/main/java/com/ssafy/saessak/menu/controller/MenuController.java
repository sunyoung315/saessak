package com.ssafy.saessak.menu.controller;

import com.ssafy.saessak.menu.dto.MenuPhotoRequestDto;
import com.ssafy.saessak.menu.dto.MenuRequestDto;
import com.ssafy.saessak.menu.dto.MenuWeekRequestDto;
import com.ssafy.saessak.menu.service.MenuService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/menu")
@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@RequestBody List<MenuRequestDto> menuRequestDtoList) {
        menuService.insert(menuRequestDtoList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> list(@RequestBody MenuWeekRequestDto menuWeekRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, menuService.list(menuWeekRequestDto)));
    }

    @PostMapping(value = "/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insertPhoto(@RequestBody MenuPhotoRequestDto menuWeekRequestDto) {
        menuService.insertPhoto(menuWeekRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
