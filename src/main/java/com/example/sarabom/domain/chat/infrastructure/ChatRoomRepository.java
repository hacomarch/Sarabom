package com.example.sarabom.domain.chat.infrastructure;

import com.example.sarabom.domain.chat.domain.ChatRoom;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.property.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 집, 주인, 문의자 로 생성된 채팅방이 있는지 조회
     */
    Optional<ChatRoom> findByPropertyAndOwnerAndInquirer(Property property, Member owner, Member inquirer);

    /**
     * 해당 회원이 참여하고 있는 채팅방 목록 조회 - 마지막에 문자 보낸 날짜 역순으로
     */
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.owner = :member OR cr.inquirer = :member ORDER BY cr.lastMessageAt DESC")
    List<ChatRoom> findByMember(@Param("member") Member member);

    /**
     * 해당 회원이 참여하고 있는 채팅방 상세 조회
     */
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.id = :roomId AND (cr.owner = :member OR cr.inquirer = :member)")
    Optional<ChatRoom> findByIdAndMember(@Param("roomId") Long roomId, @Param("member") Member member);
}
