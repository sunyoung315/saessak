package com.ssafy.saessak.chat.controller;


import com.ssafy.saessak.chat.dto.ChatMessageResponse;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.service.ChatService;
import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/chat")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;


    @Operation(summary = "학부모에 해당하는 채팅방 전체 조회")
    @GetMapping(value = "/parent/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> parentList() {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByParent();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "선생님이 관리하는 채팅방 전체 조회")
    @GetMapping(value = "/teacher/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> teacherList() {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByTeacher();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "채팅방 이전 내용 불러오기")
    @GetMapping(value = "/room/list/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> roomChatList(@PathVariable("roomId") Long roomId) {
        List<ChatMessageResponse> roomResponseDtoList = chatService.getAllChat(roomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "선생님이 채팅방 생성")
    @PostMapping(value = "/add/teacher/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> createTeacherRoom(@PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatService.addTeacherRoom(kidId)));
    }

    @Operation(summary = "학부모가 채팅방 생성")
    @PostMapping(value = "/add/parent/{teacherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> createParentRoom(@PathVariable("teacherId") Long teacherId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatService.addParentRoom(teacherId)));
    }

    @Operation(summary = "채팅 들어올 때 JWT 검증")
    @GetMapping(value = "/isVaild", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> isVaild() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatService.isValid()));
    }

    @Operation(summary = "마지막 접속 시간 기록")
    @GetMapping(value = "/disconnect", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> disconnect(Long roomId, Long userId) {
        chatService.setLastVisit(roomId, userId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
