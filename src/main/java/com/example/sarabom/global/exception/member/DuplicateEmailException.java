package com.example.sarabom.global.exception.member;

import com.example.sarabom.global.exception.BusinessException;

import static com.example.sarabom.global.common.ErrorCode.DUPLICATE_EMAIL;

/**
 * 전화번호 중복 예외
 */
public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }

    public DuplicateEmailException(String message) {
        super(DUPLICATE_EMAIL, message);
    }
}
