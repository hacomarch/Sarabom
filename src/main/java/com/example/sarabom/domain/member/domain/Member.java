package com.example.sarabom.domain.member.domain;

import com.example.sarabom.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String phoneNumber;
    private String password;
    private String nickname;
    private String address;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member of(String username, String phoneNumber, String encodedPassword, String nickname, String address) {
        return Member.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .password(encodedPassword)
                .nickname(nickname)
                .address(address)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public void withdraw() {
        this.status = MemberStatus.WITHDRAWN;
    }
}
