package com.example.sarabom.global.exception.auth;

import com.example.sarabom.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.sarabom.global.common.ErrorCode.INVALID_LOGIN;

public class InvalidLoginException extends BusinessException {
    public InvalidLoginException() {
        super(INVALID_LOGIN, HttpStatus.UNAUTHORIZED);
    }
}
