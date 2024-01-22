package com.ssafy.saessak.board.controller;


import com.ssafy.saessak.board.service.BoardService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/v2/board")
public class BoardController {


    private final BoardService boardService;
    @GetMapping("/health")
    public ResponseEntity<ResultResponse> getHealth(){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,"is alive"));
    }

}
