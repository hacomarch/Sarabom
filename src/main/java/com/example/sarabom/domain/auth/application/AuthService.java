package com.example.sarabom.domain.auth.application;

import com.example.sarabom.domain.auth.domain.RefreshToken;
import com.example.sarabom.domain.auth.dto.request.LoginRequest;
import com.example.sarabom.domain.auth.dto.request.RefreshTokenRequest;
import com.example.sarabom.domain.auth.dto.response.LoginResponse;
import com.example.sarabom.domain.auth.dto.response.RefreshTokenResponse;
import com.example.sarabom.domain.auth.infrastructure.RefreshTokenRepository;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.infrastructure.MemberRepository;
import com.example.sarabom.global.common.ApiResponse;
import com.example.sarabom.global.exception.auth.ExpiredRefreshTokenException;
import com.example.sarabom.global.exception.auth.InvalidLoginException;
import com.example.sarabom.global.exception.auth.InvalidRefreshTokenException;
import com.example.sarabom.global.exception.member.MemberNotFoundException;
import com.example.sarabom.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.sarabom.domain.member.domain.MemberStatus.ACTIVE;
import static com.example.sarabom.global.common.SuccessCode.SUCCESS_LOGOUT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    /**
     * 로그인
     */
    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        // 1. 이메일로 회원 조회 (ACTIVE 상태만)
        Member member = memberRepository.findByEmailAndStatus(request.getEmail(), ACTIVE)
                .orElseThrow(MemberNotFoundException::new);

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword().getValue())) {
            throw new InvalidLoginException();
        }

        // 3. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshTokenValue = jwtTokenProvider.createRefreshToken(member.getId());

        // 4. Refresh Token DB에 저장 (기존 토큰이 있으면 갱신, 없으면 새로 생성)
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);
        refreshTokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        token -> token.updateToken(refreshTokenValue, expiresAt),
                        () -> refreshTokenRepository.save(RefreshToken.of(refreshTokenValue, member.getId(), expiresAt))
                );

        // 5. 응답 반환
        LoginResponse data = LoginResponse.of(accessToken, refreshTokenValue, member.getId(), member.getNickname());
        return ApiResponse.success(data);
    }

    /**
     * 토큰 갱신
     */
    @Transactional
    public ApiResponse<RefreshTokenResponse> refresh(RefreshTokenRequest request) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new InvalidRefreshTokenException();
        }

        // 2. DB에서 Refresh Token 조회
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        // 3. 만료 확인
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredRefreshTokenException();
        }

        // 4. 새로운 Access Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(refreshToken.getMemberId());

        // 5. 새로운 Refresh Token 생성 및 갱신
        String newRefreshToken = jwtTokenProvider.createRefreshToken(refreshToken.getMemberId());
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);
        refreshToken.updateToken(newRefreshToken, expiresAt);

        // 6. 응답 반환
        RefreshTokenResponse data = RefreshTokenResponse.of(newAccessToken, newRefreshToken);
        return ApiResponse.success(data);
    }

    /**
     * 로그아웃
     */
    @Transactional
    public ApiResponse<Void> logout(Long memberId) {
        // DB에서 Refresh Token 삭제
        refreshTokenRepository.deleteByMemberId(memberId);
        return ApiResponse.success(SUCCESS_LOGOUT);
    }
}
