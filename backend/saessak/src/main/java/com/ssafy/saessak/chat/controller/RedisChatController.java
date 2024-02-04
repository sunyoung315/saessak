package com.ssafy.saessak.chat.controller;

import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.dto.ChatPagingRequestDto;
import com.ssafy.saessak.chat.dto.ChatPagingResponseDto;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.service.ChatRedisCacheService;
import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping("/room/list/{roomId}")
    public ResponseEntity<?> getChatRoom(@PathVariable Long roomId, @RequestBody(required = false) ChatPagingRequestDto chatPagingDto){

        try { //  Cursor 존재하지 않을 경우,현재시간을 기준으로 paging
            if(chatPagingDto.getChatTime() == null || chatPagingDto.getChatTime().equals("")){
                chatPagingDto= ChatPagingRequestDto.builder()
                        .chatTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                        .build(); // 빈 chatPagingDto 반환
            }

            List<ChatPagingResponseDto> chatList = chatRedisCacheService.getChatsFromRedis(roomId, chatPagingDto);

            return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, chatList));
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
