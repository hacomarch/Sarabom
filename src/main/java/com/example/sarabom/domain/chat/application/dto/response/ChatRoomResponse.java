package com.example.sarabom.domain.chat.application.dto.response;

import com.example.sarabom.domain.chat.domain.ChatRoom;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.property.domain.BuildingType;
import com.example.sarabom.domain.property.domain.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomResponse {

    private Long roomId;
    private PropertySummary property;
    private MemberSummary otherMember;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private Long unreadCount;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PropertySummary {
        private Long id;
        private String address;
        private BuildingType buildingType;
        private String buildingName;

        public static PropertySummary from(Property property) {
            return PropertySummary.builder()
                    .id(property.getId())
                    .address(property.getAddress().getFullAddress())
                    .buildingType(property.getBuildingType())
                    .buildingName(property.getBuildingName())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberSummary {
        private Long id;
        private String username;
        private String nickname;

        public static MemberSummary from(Member member) {
            return MemberSummary.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .build();
        }
    }

    public static ChatRoomResponse from(ChatRoom chatRoom, Member currentMember, String lastMessage, Long unreadCount) {
        Member otherMember = chatRoom.getOwner().equals(currentMember)
                ? chatRoom.getInquirer()
                : chatRoom.getOwner();

        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .property(PropertySummary.from(chatRoom.getProperty()))
                .otherMember(MemberSummary.from(otherMember))
                .lastMessage(lastMessage)
                .lastMessageAt(chatRoom.getLastMessageAt())
                .unreadCount(unreadCount)
                .build();
    }
}
