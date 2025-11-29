package com.example.sarabom.domain.chat.domain;

import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.property.domain.Property;
import com.example.sarabom.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_chat_room",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"property_id", "owner_id", "inquirer_id"}
        ))
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquirer_id", nullable = false)
    private Member inquirer;

    private LocalDateTime lastMessageAt;

    public static ChatRoom of(Property property, Member owner, Member inquirer) {
        return ChatRoom.builder()
                .property(property)
                .owner(owner)
                .inquirer(inquirer)
                .lastMessageAt(LocalDateTime.now())
                .build();
    }

    public void updateLastMessageAt() {
        this.lastMessageAt = LocalDateTime.now();
    }
}
