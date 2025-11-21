package com.example.sarabom.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private Long memberId;
    private String nickname;

    public static LoginResponse of(String accessToken, String refreshToken, Long memberId, String nickname) {
        return new LoginResponse(accessToken, refreshToken, memberId, nickname);
    }
}
