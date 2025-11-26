package com.example.sarabom.domain.member.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
