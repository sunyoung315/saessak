package com.ssafy.saessak.notice.controller;

import com.google.api.Page;
import com.ssafy.saessak.notice.dto.*;
import com.ssafy.saessak.notice.service.NoticeService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/notice")
@RequiredArgsConstructor
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    // PageNation 적용하기 -> 20개 정도?
    // 고정한 것을 상단에
    @Operation(summary = "선생님 공지사항 전체 조회")
    @GetMapping(value = "/all/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getAllNotice(@RequestParam("pageNo") int pageNo) {
        NoticeResponseListDto list = noticeService.getAllTeacherNotice(pageNo);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, list));
    }

    @Operation(summary = "학부모 공지사항 전체 조회")
    @GetMapping(value = "/all/parent/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getAllNotice(@PathVariable("kidId") Long kidId,
                                                       @RequestParam("pageNo") int pageNo) {
        NoticeResponseListDto list = noticeService.getAllParentNotice(kidId, pageNo);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, list));
    }

    @Operation(summary = "공지사항 상세 조회")
    @GetMapping(value = "/detail/{noticeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getDetailNotice(@PathVariable("noticeId") Long noticeId) {
        NoticeDetailReponseDto notice = noticeService.getDetailNotice(noticeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, notice));
    }

    @Operation(summary = "공지사항 등록")
    @PostMapping(value = "/{classroomId}", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> addNotice(@PathVariable("classroomId") Long classroomId,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("content") String content,
                                                    @RequestPart(value = "noticeFile", required = false) MultipartFile noticeFile) throws IOException {
        Long noticeId = noticeService.addNotice(classroomId, title, content, noticeFile);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, noticeId));
    }

    @Operation(summary = "학부모 공지사항 고정")
    @PostMapping(value = "/fixed/parent/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> addParentFix(@RequestBody FixedRequestDto fixedRequestDto) {
        Long noticeId = noticeService.addParentFix(fixedRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, noticeId));
    }

    @Operation(summary = "학부모 공지사항 고정 해제")
    @DeleteMapping(value = "/fixed/parent/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteParentFix(@RequestBody FixedRequestDto fixedRequestDto) {
        noticeService.deleteParentFix(fixedRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "선생님 공지사항 고정")
    @PostMapping(value = "/fixed/teacher/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> addTeacherFix(@RequestParam("noticeId") Long noticeId) {
        noticeService.addTeacherFix(noticeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "공지사항 고정 해제")
    @DeleteMapping(value = "/fixed/teacher/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteTeacherFix(@RequestParam("noticeId") Long noticeId) {
        noticeService.deleteTeacherFix(noticeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    // PageNation 적용하기 -> 1개 단위
    @Operation(summary = "고정한 공지사항 조회")
    @GetMapping(value = "/fixed/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getFixedNotice(@PathVariable("kidId") Long kidId) {
        List<NoticeResponseFixedDto> list = noticeService.getAllFixedNotice(kidId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, list));
    }

}
