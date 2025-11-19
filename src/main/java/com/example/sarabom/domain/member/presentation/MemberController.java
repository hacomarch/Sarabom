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

    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴 처리합니다. (Soft Delete)")
    @DeleteMapping("/{memberId}/withdrawal")
    public ApiResponse<Void> withdrawal(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long memberId) {
        return memberService.withdrawal(memberId);
    }

    @Operation(summary = "회원 정보 조회", description = "회원의 상세 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ApiResponse<MemberInfoResponse> getInfo(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long memberId) {
        return memberService.getMemberInfo(memberId);
    }

    @Operation(summary = "회원 정보 수정", description = "회원의 정보를 수정합니다.")
    @PatchMapping("/{memberId}")
    public ApiResponse<Void> updateInfo(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long memberId,
            @Valid @RequestBody UpdateMemberInfoRequest request) {
        return memberService.updateMemberInfo(memberId, request);
    }

    @Operation(summary = "비밀번호 변경", description = "회원의 비밀번호를 변경합니다.")
    @PatchMapping("/{memberId}/password")
    public ApiResponse<Void> changePassword(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long memberId,
            @Parameter(description = "암호화된 새 비밀번호", required = true) @RequestParam String encodedNewPassword) {
        return memberService.updatePassword(memberId, encodedNewPassword);
    }
}
