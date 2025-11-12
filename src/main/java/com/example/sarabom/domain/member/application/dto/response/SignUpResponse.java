package com.example.sarabom.domain.member.application.dto.response;

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

    /**
     * dto 생성
     * @param memberId - 회원 id
     * @param nickname - 회원 닉네임
     * @return SignUpResponse
     */
    public static SignUpResponse of(Long memberId, String nickname) {
        return SignUpResponse.builder()
                .memberId(memberId)
                .nickname(nickname)
                .build();
    }
}
