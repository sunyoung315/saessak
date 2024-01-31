package com.ssafy.saessak.config;

import com.ssafy.saessak.chat.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // WebSocketConfigurer 인터페이스를 구현
    private final StompHandler stompHandler; // jwt 인증용

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")     // SockJS 연결 주소
                .setAllowedOriginPatterns("*");      // 서버 환경에 맞게 변경 필요
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * @Method Name : enableSimpleBroker
         * 해당 주소를 구독하고 있는 클라이언트에게 메시지 전달
         */
        registry.enableSimpleBroker("/sub");

        /**
         * @Method Name : setApplicationDestinationPrefixes
         * 클라이언트에서 보낸 메시지를 받을 prefix
         */
        registry.setApplicationDestinationPrefixes("/pub");
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
}