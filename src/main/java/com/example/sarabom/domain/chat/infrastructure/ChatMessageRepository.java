package com.example.sarabom.domain.chat.infrastructure;

import com.example.sarabom.domain.chat.domain.ChatMessage;
import com.example.sarabom.domain.chat.domain.ChatRoom;
import com.example.sarabom.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 해당 채팅방의 메시지들 조회 - 생성일 역순
     */
    Page<ChatMessage> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);

    /**
     * 해당 회원이 해당 채팅방에서 안 읽은 메시지 개수 조회
     */
    Long countByChatRoomAndIsReadFalseAndSenderNot(ChatRoom chatRoom, Member member);
}
