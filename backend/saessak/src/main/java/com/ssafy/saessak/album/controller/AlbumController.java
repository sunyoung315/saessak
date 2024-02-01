package com.ssafy.saessak.album.controller;

import com.ssafy.saessak.album.dto.AlbumRequestDto;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.dto.KidAlbumResponseDto;
import com.ssafy.saessak.album.service.AlbumService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/album")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @Operation(summary = "반 앨범 전체 조회")
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<ResultResponse> getClassAlbumList(@PathVariable(name = "classroomId") Long classroomId) {
        List<AlbumResponseDto> albumResponseDtoList = albumService.getClassAlbumList(classroomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @Operation(summary = "반 앨범 날짜별 조회")
    @PostMapping("/classroom/{classroomId}")
    public ResponseEntity<ResultResponse> getClassAlbum
            (@PathVariable(name = "classroomId") Long classroomId, @RequestBody AlbumRequestDto albumRequestDto){
        log.debug("controller requestBody  : {}", albumRequestDto);
        LocalDate albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList = albumService.getClassAlbum(classroomId, albumDate);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @Operation(summary = "아이별 앨범 전체 조회")
    @GetMapping("/kid/{kidId}")
    public ResponseEntity<ResultResponse> getKidAlbumList(@PathVariable(name = "kidId") Long kidId){
        List<AlbumResponseDto> albumResponseDtoList = albumService.getKidAlbumList(kidId);
        log.debug("controller albumList : {} ", albumResponseDtoList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDtoList));
    }

    @Operation(summary = "아이별 앨범 날짜별 조회")
    @PostMapping("/kid/{kidId}")
    public ResponseEntity<ResultResponse> getKidAlbum
            (@PathVariable(name = "kidId") Long kidId, @RequestBody AlbumRequestDto albumRequestDto){
        LocalDate albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList =  albumService.getKidAlbum(kidId, albumDate);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDtoList));
    }

    @Operation(summary = "반 아이들의 가장 최근 앨범리스트")
    @GetMapping("/classroom/kid")
    public ResponseEntity<ResultResponse> getKidsCurrentAlbumList(){
        List<KidAlbumResponseDto> kidAlbumResponseDtoList = albumService.getKidsCurrentAlbum();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, kidAlbumResponseDtoList));
    }

}
