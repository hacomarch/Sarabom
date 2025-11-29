package com.example.sarabom.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 경로 설정 (서버 -> 클라이언트)
        // 클라이언트가 /topic/... 을 구독하면 메시지를 받을 수 있음
        registry.enableSimpleBroker("/topic", "/queue");

        // 발행 경로 설정 (클라이언트 -> 서버)
        // 클라이언트가 /app/... 으로 메시지를 보내면 @MessageMapping 에서 처리
        registry.setApplicationDestinationPrefixes("/app");

        // 특정 사용자에게 보낼 때 사용 (1:1 메시지용)
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트
        // 클라이언트는 ws://서버주소/ws-chat 으로 연결
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")  // CORS 설정
                .withSockJS();  // SockJS fallback 활성화
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 인증 인터셉터 추가
        registration.interceptors(webSocketAuthInterceptor);
    }
}
