package com.example.sarabom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.sarabom.global.common.SuccessCode.SUCCESS;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    // 성공 응답 (데이터 O)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    // 성공 응답 (SuccessCode 지정)
    public static <T> ApiResponse<T> success(T data, SuccessCode successCode) {
        return new ApiResponse<>(successCode.getCode(), successCode.getMessage(), data);
    }

    // 성공 응답 (데이터 X)
    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.getCode(), successCode.getMessage(), null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    // 실패 응답 (메시지 커스텀)
    public static <T> ApiResponse<T> error(ErrorCode errorCode, String customMessage) {
        return new ApiResponse<>(errorCode.getCode(), customMessage, null);
    }
}
