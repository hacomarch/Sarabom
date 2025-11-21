package com.example.sarabom.global.exception.auth;

import com.example.sarabom.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.sarabom.global.common.ErrorCode.EXPIRED_REFRESH_TOKEN;

public class ExpiredRefreshTokenException extends BusinessException {
    public ExpiredRefreshTokenException() {
        super(EXPIRED_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED);
    }
}
