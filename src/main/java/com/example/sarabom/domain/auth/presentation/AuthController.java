package com.example.sarabom.domain.auth.presentation;

import com.example.sarabom.domain.auth.application.AuthService;
import com.example.sarabom.domain.auth.dto.request.LoginRequest;
import com.example.sarabom.domain.auth.dto.request.RefreshTokenRequest;
import com.example.sarabom.domain.auth.dto.response.LoginResponse;
import com.example.sarabom.domain.auth.dto.response.RefreshTokenResponse;
import com.example.sarabom.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "로그인/로그아웃 관련 API")
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "전화번호와 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "토큰 갱신", description = "Refresh Token으로 새로운 Access Token을 발급받습니다.")
    @PostMapping("/refresh")
    public ApiResponse<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @Operation(summary = "로그아웃", description = "로그아웃하고 Refresh Token을 무효화합니다.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        return authService.logout(memberId);
    }
}
