package com.example.sarabom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 공통 (E0xx)
    INVALID_INPUT_VALUE("E000", "잘못된 입력값입니다"),
    INTERNAL_SERVER_ERROR("E001", "서버 내부 오류가 발생했습니다"),

    // 회원 (E1xx)
    MEMBER_NOT_FOUND("E100", "회원을 찾을 수 없습니다"),
    DUPLICATE_PHONE_NUMBER("E101", "이미 가입된 전화번호입니다"),
    INVALID_PASSWORD("E102", "비밀번호가 일치하지 않습니다")
    ;

    private final String code;
    private final String message;
}
