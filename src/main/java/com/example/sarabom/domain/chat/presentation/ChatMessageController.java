package com.example.sarabom.domain.chat.presentation;

import com.example.sarabom.domain.chat.application.ChatService;
import com.example.sarabom.domain.chat.application.dto.request.SendMessageRequest;
import com.example.sarabom.domain.chat.application.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload SendMessageRequest request, Authentication authentication) {
        try {
            // 보내는 사람 조회
            Long senderId = (Long) authentication.getPrincipal();

            // 메시지 전송
            ChatMessageResponse response = chatService.sendMessage(
                    request.getRoomId(),
                    senderId,
                    request.getContent()
            );

            // 해당 채팅방을 구독하고 있는 사람에게 메시지 전송
            messagingTemplate.convertAndSend(
                    "/topic/chat/" + request.getRoomId(),
                    response
            );

            log.info("Message sent to room {}: {}", request.getRoomId(), request.getContent());
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage(), e);
            // Optionally send error message back to the sender
            messagingTemplate.convertAndSendToUser(
                    authentication.getName(),
                    "/queue/errors",
                    "Failed to send message: " + e.getMessage()
            );
        }
    }
}
