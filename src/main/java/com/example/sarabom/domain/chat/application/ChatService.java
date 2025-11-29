package com.example.sarabom.domain.chat.application;

import com.example.sarabom.domain.chat.application.dto.response.ChatMessageResponse;
import com.example.sarabom.domain.chat.application.dto.response.ChatRoomResponse;
import com.example.sarabom.domain.chat.domain.ChatMessage;
import com.example.sarabom.domain.chat.domain.ChatRoom;
import com.example.sarabom.domain.chat.domain.MessageType;
import com.example.sarabom.domain.chat.infrastructure.ChatMessageRepository;
import com.example.sarabom.domain.chat.infrastructure.ChatRoomRepository;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.infrastructure.MemberRepository;
import com.example.sarabom.domain.property.domain.Property;
import com.example.sarabom.domain.property.infrastructure.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final PropertyRepository propertyRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅방 생성
     */
    @Transactional
    public ChatRoomResponse createChatRoom(Long propertyId, Long inquirerId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found: " + propertyId));

        Member inquirer = memberRepository.findById(inquirerId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + inquirerId));

        Member owner = property.getMember();

        // 기존에 채팅방이 있는지 확인 후, 없으면 생성하고, 있으면 기존 채팅방 반환
        ChatRoom chatRoom = chatRoomRepository
                .findByPropertyAndOwnerAndInquirer(property, owner, inquirer)
                .orElseGet(() -> {
                    // 새로운 채팅방 생성 및 저장
                    ChatRoom newRoom = ChatRoom.of(property, owner, inquirer);
                    ChatRoom savedRoom = chatRoomRepository.save(newRoom);

                    // 채팅 시작 알림 메시지 전송
                    ChatMessage systemMessage = ChatMessage.of(
                            savedRoom,
                            inquirer,
                            "채팅이 시작되었습니다.",
                            MessageType.SYSTEM
                    );
                    chatMessageRepository.save(systemMessage);

                    return savedRoom;
                });

        return ChatRoomResponse.from(chatRoom, inquirer, null, 0L);
    }

    /**
     * 채팅방 목록 조회
     */
    public List<ChatRoomResponse> getChatRooms(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        List<ChatRoom> chatRooms = chatRoomRepository.findByMember(member);

        return chatRooms.stream()
                .map(room -> {
                    // 각 채팅방의 마지막 메시지 조회
                    Page<ChatMessage> messages = chatMessageRepository
                            .findByChatRoomOrderByCreatedAtDesc(room, Pageable.ofSize(1));
                    String lastMessage = messages.hasContent() ? messages.getContent().get(0).getContent() : null;

                    // 해당 채팅방에서 해당 회원이 읽지 않은 메시지 개수 조회
                    Long unreadCount = chatMessageRepository
                            .countByChatRoomAndIsReadFalseAndSenderNot(room, member);

                    return ChatRoomResponse.from(room, member, lastMessage, unreadCount);
                })
                .toList();
    }

    /**
     * 채팅방의 메시지들 조회
     */
    public Page<ChatMessageResponse> getChatMessages(Long roomId, Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found or access denied: " + roomId));

        // 해당 채팅방의 메시지들 조회
        Page<ChatMessage> messages = chatMessageRepository
                .findByChatRoomOrderByCreatedAtDesc(chatRoom, pageable);

        return messages.map(ChatMessageResponse::from);
    }

    /**
     * 메시지 전송
     */
    @Transactional
    public ChatMessageResponse sendMessage(Long roomId, Long senderId, String content) {
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + senderId));

        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, sender)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found or access denied: " + roomId));

        // 메시지 생성 및 저장
        ChatMessage message = ChatMessage.of(chatRoom, sender, content, MessageType.TEXT);
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // 마지막 메시지 전송 날짜 업데이트
        chatRoom.updateLastMessageAt();

        return ChatMessageResponse.from(savedMessage);
    }

    /**
     * 메시지 읽음 확인
     */
    @Transactional
    public void markMessagesAsRead(Long roomId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found or access denied: " + roomId));

        // 해당 채팅방의 메시지들 조회
        Page<ChatMessage> unreadMessages = chatMessageRepository
                .findByChatRoomOrderByCreatedAtDesc(chatRoom, Pageable.unpaged());

        // 메시지들 중에서 내가 안 읽은 메시지들만 필터링 후 읽음 확인 처리
        unreadMessages.getContent().stream()
                .filter(msg -> !msg.getSender().equals(member) && !msg.getIsRead())
                .forEach(ChatMessage::markAsRead);
    }
}
