package com.example.sarabom.global.exception.auth;

import com.example.sarabom.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.sarabom.global.common.ErrorCode.INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException() {
        super(INVALID_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED);
    }
}
