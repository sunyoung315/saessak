package com.ssafy.saessak.album.controller;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.dto.AlbumRequestDto;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.service.AlbumService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/album")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/class/{classroomId}")
    public ResponseEntity<ResultResponse> getClassAlbumList(@PathVariable(name = "classroomId") Long classroomId) {
        List<AlbumResponseDto> albumResponseDtoList = albumService.getClassAlbumList(classroomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @PostMapping("/class/{classroomId}")
    public ResponseEntity<ResultResponse> getClassAlbum
            (@PathVariable(name = "classroomId") Long classroomId, @RequestBody AlbumRequestDto albumRequestDto){
        log.debug("controller requestBody  : {}", albumRequestDto);
        Date albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList = albumService.getClassAlbum(classroomId, albumDate);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albumResponseDtoList));
    }
    @GetMapping("/kid/{kidId}")
    public ResponseEntity<ResultResponse> getKidAlbumList(@PathVariable(name = "kidId") Long kidId){
        List<AlbumResponseDto> albumResponseDtoList = albumService.getKidAlbumList(kidId);
        log.debug("controller albumList : {} ", albumResponseDtoList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDtoList));
    }

    @PostMapping("/kid/{kidId}")
    public ResponseEntity<ResultResponse> getKidAlbum
            (@PathVariable(name = "kidId") Long kidId, @RequestBody AlbumRequestDto albumRequestDto){
        Date albumDate = albumRequestDto.getAlbumDate();
        List<AlbumResponseDto> albumResponseDtoList =  albumService.getKidAlbum(kidId, albumDate);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, albumResponseDtoList));
    }

}
