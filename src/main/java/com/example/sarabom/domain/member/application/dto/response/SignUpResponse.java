package com.example.sarabom.domain.member.application.dto.response;

import com.example.sarabom.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
    private Long memberId;
    private String nickname;

    public static SignUpResponse from(Member member) {
        return SignUpResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
