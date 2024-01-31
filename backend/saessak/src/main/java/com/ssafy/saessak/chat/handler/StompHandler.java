package com.ssafy.saessak.chat.handler;


import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.oauth.jwt.JwtValidationType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String authorization = accessor.getFirstNativeHeader("Authorization");
        String token = authorization.replace("Bearer ", "");
        System.out.println("authorization " + authorization);

        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(jwtProvider.validateToken(token) != JwtValidationType.VALID_JWT) {
                throw new RuntimeException("Not Valid Token");
            }
        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == null) {
            return;  // 메시지에 STOMP 명령이 없는 경우는 무시
        }

        // DISCONNECT 명령 처리
        if (accessor.getCommand() == StompCommand.DISCONNECT) {
            String username = accessor.getUser().getName();
            System.out.println(username);
        }
    }

}