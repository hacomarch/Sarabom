package com.example.sarabom.global.exception.member;

import com.example.sarabom.global.common.ErrorCode;
import com.example.sarabom.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.sarabom.global.common.ErrorCode.INVALID_PASSWORD;

public class InvalidPassword extends BusinessException {
    public InvalidPassword() {
        super(INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
    }

    public InvalidPassword(ErrorCode errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }
}
