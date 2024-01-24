package com.ssafy.saessak.chat.controller;


import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.service.ChatService;
import com.ssafy.saessak.document.dto.AllergyRequestDto;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
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

    @GetMapping(value = "/parent/list/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> parentList(@PathVariable("parentId") Long parentId) {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByParent(parentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, roomResponseDtoList));
    }

    @GetMapping(value = "/teacher/list/{teacherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> teacherList(@PathVariable("teacherId") Long teacherId) {
        List<RoomResponseDto> roomResponseDtoList = chatService.getRoomByTeacher(teacherId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @PostMapping(value = "/add/{teacherId}/{kidId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> createRoom(@PathVariable("teacherId") Long teacherId, @PathVariable("kidId") Long kidId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatService.addRoom(teacherId, kidId)));
    }
}
