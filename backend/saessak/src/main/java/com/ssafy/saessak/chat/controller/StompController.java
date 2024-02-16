package com.ssafy.saessak.chat.controller;

import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.service.ChatRedisCacheService;
import com.ssafy.saessak.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class StompController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatRedisCacheService chatRedisCacheService;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @MessageMapping("/message")     // 클라이언트에서 /pub/message 로 메시지를 발행한다.
    public void message(ChatMessage message) {
        // 메시지 처리 로직
        message.setChatTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));
        chatRedisCacheService.addChat(message);

        // 메시지에 정의된 채널 id에 메시지를 보낸다.
        sendingOperations.convertAndSend("/sub/room/"+message.getRoomId(), message);
    }

    @MessageMapping("/request")
    public void request(int roomId){
        // 선생님 -> 학부모 화상채팅 요청
        sendingOperations.convertAndSend("/sub/room/"+roomId, "videoChat allow request");
    }

    @MessageMapping("/response")
    public void response(int roomId){
        // 상대에게 alert 을 띄우도록 한다.
        sendingOperations.convertAndSend("/sub/room/"+roomId, "videoChat deny response");
    }
}
