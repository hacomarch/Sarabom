package com.example.sarabom.global.exception.member;

import com.example.sarabom.global.exception.BusinessException;

import static com.example.sarabom.global.common.ErrorCode.DUPLICATE_PHONE_NUMBER;

/**
 * 전화번호 중복 예외
 */
public class DuplicatePhoneNumberException extends BusinessException {

    public DuplicatePhoneNumberException() {
        super(DUPLICATE_PHONE_NUMBER);
    }

    public DuplicatePhoneNumberException(String message) {
        super(DUPLICATE_PHONE_NUMBER, message);
    }
}
