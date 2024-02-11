package com.ssafy.saessak.album.controller;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.dto.AlbumRequestDto;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.dto.KidAlbumResponseDto;
import com.ssafy.saessak.album.service.AlbumService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
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

    @Operation(summary = "가장 최근 엘범 조회 ( 선생님 ) ")
    @GetMapping("/class/current")
    public ResponseEntity<ResultResponse> getClassCurrentAlbum(){
        AlbumResponseDto albumResponseDto = albumService.getTeacherCurrentAlbum();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDto));
    }

    @Operation(summary = "엘범이 있는 날짜 리스트 (선생님)")
    @GetMapping("/exist")
    public ResponseEntity<ResultResponse> getExistAlbumDate() {

        List<LocalDate> result = albumService.getExistAlbumDate();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }
    @Operation(summary = "앨범이 있는 날짜 리스트 (학부모)")
    @GetMapping("/exist/{kidId}")
    public ResponseEntity<ResultResponse> getKidExistAlbumDate(@PathVariable(name = "kidId") Long kidId) {
        List<LocalDate> result = albumService.getKidExistAlbumDate(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,result));
    }

    @Operation(summary = "albumId로 조회")
    @GetMapping("/detail/{albumId}")
    public ResponseEntity<ResultResponse> getAlbumUsingId(@PathVariable(name = "albumId") Long albumId){
        AlbumResponseDto albumResponseDto = albumService.getAlbumUsingId(albumId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDto));
    }

    @Operation(summary = "반 앨범 전체 조회(선생님)")
    @GetMapping("/classroom")
    public ResponseEntity<ResultResponse> getTeacherClassAlbumList() {
        List<AlbumResponseDto> albumResponseDtoList = albumService.getTeacherClassAlbumList();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @Operation(summary = "반 앨범 전체 조회(학부모)")
    @GetMapping("/classroom/{kidId}")
    public ResponseEntity<ResultResponse> getParentClassAlbumList(@PathVariable(name = "kidId") Long kidId){
        List<AlbumResponseDto> albumResponseDtoList = albumService.getParentClassAlbumList(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @Operation(summary = "반 앨범 날짜별 조회(학부모)")
    @PostMapping("/classroom/{kidId}")
    public ResponseEntity<ResultResponse> getClassAlbumUsingDate
            (@PathVariable(name = "kidId") Long kidId, @RequestBody AlbumRequestDto albumRequestDto){
        log.debug("controller requestBody  : {}", albumRequestDto);
        LocalDate albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList = albumService.getClassAlbum(kidId, albumDate);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @Operation(summary = "반 앨범 날짜별 조회(선생님)")
    @PostMapping("/classroom")
    public ResponseEntity<ResultResponse> getTeacherClassAlbumUsingDate(@RequestBody AlbumRequestDto albumRequestDto){

        LocalDate albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList = albumService.getTeacherClassAlbum(albumDate);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDtoList));
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
