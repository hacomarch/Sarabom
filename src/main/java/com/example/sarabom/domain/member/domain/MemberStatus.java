package com.example.sarabom.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    ACTIVE("활성"),
    WITHDRAWN("탈퇴"),
    BANNED("정지"),
    DORMANT("휴면")
    ;
    private String text;
}
