package com.example.sarabom.domain.member.domain;

import com.example.sarabom.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public static Member of(String username, String phoneNumber, String password, String nickname, String address) {
        return Member.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .password(password)
                .nickname(nickname)
                .address(address)
                .build();
    }
}
