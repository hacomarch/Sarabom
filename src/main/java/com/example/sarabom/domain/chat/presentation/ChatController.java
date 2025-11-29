package com.example.sarabom.domain.chat.presentation;

import com.example.sarabom.domain.chat.application.ChatService;
import com.example.sarabom.domain.chat.application.dto.request.CreateChatRoomRequest;
import com.example.sarabom.domain.chat.application.dto.response.ChatMessageResponse;
import com.example.sarabom.domain.chat.application.dto.response.ChatRoomResponse;
import com.example.sarabom.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.sarabom.global.common.SuccessCode.*;

@Tag(name = "채팅", description = "채팅 관리 API")
@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "채팅방 생성", description = "거주지에 대한 채팅방을 생성합니다. 이미 존재하면 기존 채팅방을 반환합니다.")
    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponse> createChatRoom(
            Authentication authentication,
            @Valid @RequestBody CreateChatRoomRequest request) {
        Long memberId = (Long) authentication.getPrincipal();
        ChatRoomResponse response = chatService.createChatRoom(request.getPropertyId(), memberId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "채팅방 목록 조회", description = "내가 속한 채팅방 목록을 조회합니다.")
    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponse>> getChatRooms(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        List<ChatRoomResponse> response = chatService.getChatRooms(memberId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "메시지 히스토리 조회", description = "채팅방의 메시지 목록을 조회합니다. (페이징)")
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<Page<ChatMessageResponse>> getChatMessages(
            Authentication authentication,
            @PathVariable Long roomId,
            @PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long memberId = (Long) authentication.getPrincipal();
        Page<ChatMessageResponse> response = chatService.getChatMessages(roomId, memberId, pageable);
        return ApiResponse.success(response);
    }

    @Operation(summary = "메시지 읽음 처리", description = "채팅방의 안읽은 메시지를 모두 읽음 처리합니다.")
    @PutMapping("/rooms/{roomId}/read")
    public ApiResponse<Void> markMessagesAsRead(
            Authentication authentication,
            @PathVariable Long roomId) {
        Long memberId = (Long) authentication.getPrincipal();
        chatService.markMessagesAsRead(roomId, memberId);
        return ApiResponse.success(SUCCESS);
    }
}
