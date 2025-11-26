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

    public static MemberInfoResponse of(
            Long memberId,
            String username,
            String email,
            String phoneNumber,
            String nickname,
            MemberStatus status
    ) {
        return MemberInfoResponse.builder()
                .memberId(memberId)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .status(status.getText())
                .build();
    }
}
