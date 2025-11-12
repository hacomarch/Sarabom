package com.example.sarabom.domain.member.application;

import com.example.sarabom.domain.member.application.dto.request.SignUpRequest;
import com.example.sarabom.domain.member.application.dto.response.SignUpResponse;
import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.infrastructure.MemberRepository;
import com.example.sarabom.global.common.ApiResponse;
import com.example.sarabom.global.exception.member.DuplicatePhoneNumberException;
import com.example.sarabom.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public ApiResponse<SignUpResponse> signUp(SignUpRequest request) {
        // 전화번호 중복 검증
        if (memberRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicatePhoneNumberException();
        }

        // 테이블에 저장
        Member newMember = Member.of(
                request.getUsername(),
                request.getPhoneNumber(),
                request.getPassword(),
                request.getNickname(),
                request.getAddress()
        );
        memberRepository.save(newMember);

        // 저장된 회원의 전화번호로 다시 테이블에서 정보 조회
        Member findMember = memberRepository.findByPhoneNumber(newMember.getPhoneNumber())
                .orElseThrow(MemberNotFoundException::new);

        // 필요한 값들 리턴
        SignUpResponse data = SignUpResponse.of(findMember.getId(), findMember.getNickname());
        return ApiResponse.success(data);
    }

    /**
     * 탈퇴
     */

    /**
     * 회원 조회
     */

    /**
     * 회원 정보 수정
     */

    /**
     * 비밀번호 변경
     */

}
