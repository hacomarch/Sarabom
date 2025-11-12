package com.example.sarabom.global.exception.member;

import com.example.sarabom.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.sarabom.global.common.ErrorCode.MEMBER_NOT_FOUND;

/**
 * 회원 없음 예외
 */
public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(MEMBER_NOT_FOUND, message, HttpStatus.NOT_FOUND);
    }
}
