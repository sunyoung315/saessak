package com.ssafy.saessak.board.controller;


import com.ssafy.saessak.board.domain.Board;
import com.ssafy.saessak.board.dto.*;
import com.ssafy.saessak.board.service.BoardService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/board")
@Slf4j
public class BoardController {


    private final BoardService boardService;

    // 아이 알림장 전체 조회
    @Operation(summary = "아이 알림장 전체 조회")
    @GetMapping("/{kidId}")
    public ResponseEntity<ResultResponse> getBoardList(@PathVariable(name = "kidId") Long kidId){
        List<BoardResponseDto> result = boardService.findByKid(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }

    @Operation(summary = "알림장이 있는 날짜 리스트 ")
    @GetMapping("/exist/{kidId}")
    public ResponseEntity<ResultResponse> getDateExistBoard(@PathVariable(name = "kidId") Long kidID){
        List<LocalDate> result = boardService.getDateExistBoard(kidID);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,result));
    }

    @Operation(summary = "아이 알림장 월별 조회 (학부모)")
    @PostMapping("/month/{kidId}")
    public ResponseEntity<ResultResponse> getMonthlyBoardList
            (@PathVariable(name="kidId") Long kidId , @RequestBody YearMonthRequestDto yearMonthRequestDto){
        log.debug("controller data : {}", yearMonthRequestDto );
        List<BoardResponseDto> result =
                boardService.getMonthlyKidBoardList(kidId, yearMonthRequestDto.getYear(), yearMonthRequestDto.getMonth());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }
    @Operation(summary =  "오늘 작성된 알림장이 없는 아이 리스트 ( 선생님 )")
    @GetMapping("/teacher/day")
    public ResponseEntity<ResultResponse> getClassKidBoardIsNotWritten(@RequestParam String today) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(today, formatter);
        List<KidNoBoardResponseDto> result = boardService.getClassKidBoardIsNotWritten(date);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }
    // 알림장 등록
    @Operation(summary = "알림장 등록")
    @PostMapping("")
    public ResponseEntity<ResultResponse> insertBoard(@RequestBody BoardRequestDto boardRequestDto){
        Board board = boardService.saveBoard(boardRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, board.getBoardId()));
    }
    // 알림장 조회
    @Operation(summary = "boardId로 알림장 조회")
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<ResultResponse> readBoard(@PathVariable(name = "boardId") Long boardId){
        BoardDetailDto boardDetailDto = boardService.readBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, boardDetailDto));
    }
    // 알림장 삭제
    @Operation(summary = "알림장 삭제")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResultResponse> deleteBoard(@PathVariable(name = "boardId") Long boardId){
        Long result = boardService.deleteBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }
    // 알림장 수정

    // 달력을 통해 알림장 조회
    @Operation(summary = "달력을 통해 알림장 조회")
    @PostMapping("/day/{kidId}")
    public ResponseEntity<ResultResponse> getBoardByDate(@PathVariable (name = "kidId") Long kidId,
                                                         @RequestBody DateRequestDto dateRequestDto){
        log.debug("받은 날짜 데이터 : {} " , dateRequestDto.getBoardDate());

        BoardDetailDto baordBoardDetailDto =  boardService.findByKidAndDate(kidId, dateRequestDto.getBoardDate());

        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, baordBoardDetailDto));
    }
    // 아이의 가장 최근 알림장 조회
    @Operation(summary = "아이의 가장 최근 알림장 조회")
    @GetMapping("/current/{kidId}")
    public ResponseEntity<ResultResponse> getKidCurrentBoard(@PathVariable(name = "kidId") Long kidId){
        BoardDetailDto boardDetailDto = boardService.getKidCurrentBoard(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, boardDetailDto));
    }

    @Operation(summary = "기간 아이 성장 조회")
    @PostMapping("/physical/{kidId}")
    public ResponseEntity<ResultResponse> getPhysicalList(@PathVariable (name= "kidId") Long kidId,
                                                          @RequestBody DateBetweenRequestDto dateBetweenRequestDto){
        log.debug("받은 날짜 데이터 : {} ~ {} ", dateBetweenRequestDto.getStartDate(), dateBetweenRequestDto.getEndDate());
        PhysicalResponseDto result = boardService.getPhysicalList(kidId,
                dateBetweenRequestDto.getStartDate(), dateBetweenRequestDto.getEndDate());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }

    @Operation(summary = "기간 아이 content조회")
    @PostMapping("/summary/{kidId}")
    public ResponseEntity<ResultResponse> getContentList(@PathVariable(name = "kidId") Long kidId,
                                                         @RequestBody DateBetweenRequestDto dateBetweenRequestDto){

        List<ContentResponseDto> result = boardService.getContentList(kidId,
                dateBetweenRequestDto.getStartDate(), dateBetweenRequestDto.getEndDate());


        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, result));
    }
}
