package com.example.sarabom.domain.member.presentation;

import com.example.sarabom.domain.member.application.MemberService;
import com.example.sarabom.domain.member.application.dto.request.SignUpRequest;
import com.example.sarabom.domain.member.application.dto.response.SignUpResponse;
import com.example.sarabom.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/signup")
    public ApiResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return memberService.signUp(request);
    }
}
