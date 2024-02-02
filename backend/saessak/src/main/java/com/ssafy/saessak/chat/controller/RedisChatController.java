package com.ssafy.saessak.chat.controller;

import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.service.ChatRedisCacheService;
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
public class RedisChatController {
    private final ChatRedisCacheService chatRedisCacheService;

    @PostMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> addChat(@RequestBody ChatMessage chatMessage) {
        chatRedisCacheService.addChat(chatMessage);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
