package com.ssafy.saessak.chat.controller;

import com.ssafy.saessak.chat.dto.ChatMessageRequest;
import com.ssafy.saessak.chat.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;

    @MessageMapping("/message")     // 클라이언트에서 /pub/message 로 메시지를 발행한다.
    public void message(ChatMessageRequest message) {

//        로그인 정보 기반으로 sender 설정 -> 로그인 구현 후 추가
//        String token = JwtHeaderUtil.getAccessToken(request);
//        message.setSenderId(authService.getAuthId(token));

        chatService.saveMessage(message);
        // 메시지에 정의된 채널 id에 메시지를 보낸다.
        // /sub/room/방아이디 에 구독중인 쿨라이언트에게 메시지를 보낸다.
        sendingOperations.convertAndSend("/sub/room/"+message.getRoomId(), message);
    }
}
