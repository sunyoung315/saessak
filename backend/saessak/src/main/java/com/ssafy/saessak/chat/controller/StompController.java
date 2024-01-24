package com.ssafy.saessak.chat.controller;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.dto.ChatMessageRequest;
import com.ssafy.saessak.chat.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")
    public Chat SocketHandler(Chat chat){
        Long senderId = chat.getSenderId();
        Long receiverId = chat.getReceiverId();
        String chatContent = chat.getChatContent();

        Chat result = new Chat();
    }
//            chatService.saveMessage(message);
//    public void message(HttpServletRequest request, ChatMessageRequest message) {
//
////        로그인 정보 기반으로 sender 설정 -> 로그인 구현 후 추가
////        String token = JwtHeaderUtil.getAccessToken(request);
////        message.setSenderId(authService.getAuthId(token));
//
//        // 메시지에 정의된 채널 id에 메시지를 보낸다.
//        // /sub/room/방아이디 에 구독중인 쿨라이언트에게 메시지를 보낸다.
//        sendingOperations.convertAndSend("/room/"+message.getRoomId(), message);
//    }
}
