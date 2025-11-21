package com.example.sarabom.domain.member.presentation;

import com.example.sarabom.domain.member.application.MemberService;
import com.example.sarabom.domain.member.application.dto.request.SignUpRequest;
import com.example.sarabom.domain.member.application.dto.request.UpdateMemberInfoRequest;
import com.example.sarabom.domain.member.application.dto.response.MemberInfoResponse;
import com.example.sarabom.domain.member.application.dto.response.SignUpResponse;
import com.example.sarabom.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 관리", description = "회원 CRUD API")
@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping("/signup")
    public ApiResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return memberService.signUp(request);
    }

    @Operation(summary = "회원 정보 조회", description = "내 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<MemberInfoResponse> getInfo(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.getMemberInfo(memberId);
    }

    @Operation(summary = "회원 정보 수정", description = "내 정보를 수정합니다.")
    @PatchMapping
    public ApiResponse<Void> updateInfo(
            Authentication authentication,
            @Valid @RequestBody UpdateMemberInfoRequest request) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.updateMemberInfo(memberId, request);
    }

    @Operation(summary = "비밀번호 변경", description = "내 비밀번호를 변경합니다.")
    @PatchMapping("/password")
    public ApiResponse<Void> changePassword(
            Authentication authentication,
            @Parameter(description = "새 비밀번호", required = true) @RequestParam String newPassword) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.updatePassword(memberId, newPassword);
    }

    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴 처리합니다. (Soft Delete)")
    @DeleteMapping
    public ApiResponse<Void> withdrawal(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        return memberService.withdrawal(memberId);
    }
}
