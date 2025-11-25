package com.example.sarabom.domain.member.domain;

import com.example.sarabom.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "tbl_member")
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
    @Embedded
    private Password password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member of(String username, String phoneNumber, Password password, String nickname) {
        return Member.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .password(password)
                .nickname(nickname)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public void withdraw() {
        this.status = MemberStatus.WITHDRAWN;
    }

    public void update(String username, String phoneNumber, String nickname) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public void updatePassword(Password newPassword) {
        this.password = newPassword;
    }
}
