package com.ssafy.saessak.album.controller;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.service.AlbumService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/album")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{classroomId}")
    public ResponseEntity<ResultResponse> test(@PathVariable(name = "classroomId") Long classroomId) {
        log.debug("classroom Id : {}", classroomId);
        List<Album> albums = albumService.getClassAlbumList(classroomId);
        log.debug("album size : {}", albums.size());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,albums));
    }
}
