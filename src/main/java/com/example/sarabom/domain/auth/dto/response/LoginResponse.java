package com.example.sarabom.domain.auth.dto.response;

import com.example.sarabom.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private Long memberId;
    private String nickname;

    public static LoginResponse from(String accessToken, String refreshToken, Member member) {
        return new LoginResponse(accessToken, refreshToken, member.getId(), member.getNickname());
    }
}
