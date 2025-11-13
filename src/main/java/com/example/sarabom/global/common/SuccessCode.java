package com.example.sarabom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 공통
    SUCCESS("200", "OK"),

    // 회원
    SUCCESS_WITHDRAWAL("200", "탈퇴가 완료되었습니다.")
    ;

    private final String code;
    private final String message;
}
