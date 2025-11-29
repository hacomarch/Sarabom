package com.example.sarabom.domain.member.application.dto.response;

import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.domain.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private Long memberId;
    private String username;
    private String email;
    private String phoneNumber;
    private String nickname;
    private String status;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .status(member.getStatus().getText())
                .build();
    }
}
