package com.ssafy.saessak.menu.controller;

import com.ssafy.saessak.menu.dto.MenuRequestDto;
import com.ssafy.saessak.menu.dto.MenuWeekRequestDto;
import com.ssafy.saessak.menu.service.MenuService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
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

    @PostMapping(value = "/{daycardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> insert(@PathVariable Long daycardId, @RequestBody List<MenuRequestDto> menuRequestDtoList) {
        menuService.insert(daycardId, menuRequestDtoList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> list(@RequestBody MenuWeekRequestDto menuWeekRequestDto) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, menuService.list(menuWeekRequestDto)));
    }

    @PostMapping(value = "/photo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResultResponse> insertPhoto(@RequestParam("menuId") Long menuId,
                                                      @RequestPart("MultipartFile") MultipartFile menuFile) {
        try {
            menuService.insertPhoto(menuId, menuFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
