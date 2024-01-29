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
    @GetMapping(value = "/parent/list/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> parentList(@PathVariable("parentId") Long parentId) {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByParent(parentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "선생님이 관리하는 채팅방 전체 조회")
    @GetMapping(value = "/teacher/list/{teacherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> teacherList(@PathVariable("teacherId") Long teacherId) {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByTeacher(teacherId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "채팅방 이전 내용 불러오기")
    @GetMapping(value = "/room/list/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> roomChatList(@PathVariable("roomId") Long roomId) {
        List<ChatMessageResponse> roomResponseDtoList = chatService.getAllChat(roomId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @Operation(summary = "채팅방 생성")
    @PostMapping(value = "/add/{teacherId}/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> createRoom(@PathVariable("teacherId") Long teacherId, @PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatService.addRoom(teacherId, kidId)));
    }
}
