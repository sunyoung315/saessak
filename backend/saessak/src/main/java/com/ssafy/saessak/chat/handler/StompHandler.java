package com.ssafy.saessak.chat.handler;


import com.ssafy.saessak.chat.service.ChatService;
import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.oauth.jwt.JwtValidationType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtProvider;
    private final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String authorization = accessor.getFirstNativeHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        System.out.println("authorization " + authorization);

        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(jwtProvider.validateToken(token) != JwtValidationType.VALID_JWT_TOKEN) {
                throw new RuntimeException("Not Valid Token");
            }
        } else if (accessor.getCommand() == StompCommand.UNSUBSCRIBE) {

            String roomId = Optional.ofNullable(
                    (String) message.getHeaders().get("roomId")
            ).orElse(null);

            String userId = Optional.ofNullable(
                    (String) message.getHeaders().get("userId")
            ).orElse(null);

            System.out.println(Long.parseLong(roomId) + " " + Long.parseLong(userId));
            chatService.setLastVisit(Long.parseLong(roomId), Long.parseLong(userId));
            System.out.println("closed");
        }
        return message;
    }

}