package com.example.sarabom.domain.member.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberInfoRequest {

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
}
