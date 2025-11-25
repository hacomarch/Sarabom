package com.example.sarabom.domain.member.application;

import com.example.sarabom.domain.member.application.dto.request.SignUpRequest;
import com.example.sarabom.domain.member.application.dto.request.UpdateMemberInfoRequest;
import com.example.sarabom.domain.member.application.dto.response.MemberInfoResponse;
import com.example.sarabom.domain.member.application.dto.response.SignUpResponse;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.domain.Password;
import com.example.sarabom.domain.member.infrastructure.MemberRepository;
import com.example.sarabom.global.common.ApiResponse;
import com.example.sarabom.global.exception.member.DuplicatePhoneNumberException;
import com.example.sarabom.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sarabom.domain.member.domain.MemberStatus.ACTIVE;
import static com.example.sarabom.global.common.SuccessCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public ApiResponse<SignUpResponse> signUp(SignUpRequest request) {
        // ACTIVE 상태인 회원 중 전화번호 중복 검증
        if (memberRepository.existsByPhoneNumberAndStatus(request.getPhoneNumber(), ACTIVE)) {
            throw new DuplicatePhoneNumberException();
        }

        // 비밀번호 검증 및 인코딩 후 테이블에 저장
        Password rawPassword = Password.of(request.getPassword());  // 검증
        String encoded = passwordEncoder.encode(rawPassword.getValue());  // 인코딩
        Password encodedPassword = Password.ofEncoded(encoded);  // Password 객체 생성

        Member newMember = Member.of(
                request.getUsername(),
                request.getPhoneNumber(),
                encodedPassword,
                request.getNickname()
        );
        memberRepository.save(newMember);

        // 저장된 회원의 전화번호로 다시 테이블에서 정보 조회
        Member findMember = memberRepository.findByPhoneNumberAndStatus(newMember.getPhoneNumber(), ACTIVE)
                .orElseThrow(MemberNotFoundException::new);

        // 필요한 값들 리턴
        SignUpResponse data = SignUpResponse.of(findMember.getId(), findMember.getNickname());
        return ApiResponse.success(data);
    }

    /**
     * 탈퇴
     */
    @Transactional
    public ApiResponse<Void> withdrawal(Long memberId) {
        // 회원 조회
        Member member = findById(memberId);

        // 탈퇴 - 회원 상태 변경
        member.withdraw();

        return ApiResponse.success(SUCCESS_WITHDRAWAL);
    }

    /**
     * 회원 조회
     */
    public ApiResponse<MemberInfoResponse> getMemberInfo(Long memberId) {
        Member member = findById(memberId);

        MemberInfoResponse data = MemberInfoResponse.of(
                memberId,
                member.getUsername(),
                member.getPhoneNumber(),
                member.getNickname(),
                member.getStatus()
        );

        return ApiResponse.success(data);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public ApiResponse<Void> updateMemberInfo(Long memberId, UpdateMemberInfoRequest request) {
        Member member = findById(memberId);

        member.update(request.getUsername(), request.getPhoneNumber(), request.getNickname());

        return ApiResponse.success(SUCCESS_UPDATE);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public ApiResponse<Void> updatePassword(Long memberId, String newPassword) {
        Member member = findById(memberId);

        // 비밀번호 검증 및 인코딩
        Password rawPassword = Password.of(newPassword);  // 검증
        String encoded = passwordEncoder.encode(rawPassword.getValue());  // 인코딩
        Password encodedPassword = Password.ofEncoded(encoded);  // Password 객체 생성

        member.updatePassword(encodedPassword);

        return ApiResponse.success(SUCCESS_CHANGE_PASSWORD);
    }

    // findById
    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
