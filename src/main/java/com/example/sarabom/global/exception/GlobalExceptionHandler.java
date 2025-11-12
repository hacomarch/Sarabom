package com.example.sarabom.global.exception;

import com.example.sarabom.global.common.ApiResponse;
import com.example.sarabom.global.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.example.sarabom.global.common.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.sarabom.global.common.ErrorCode.INVALID_INPUT_VALUE;

/**
 * 전역 예외 처리기
 * 애플리케이션 전역에서 발생하는 예외를 처리합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 검증 실패 시 발생하는 예외 처리
     * 여러 필드의 검증 에러를 모두 반환합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        log.warn("Validation error occurred: {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(INVALID_INPUT_VALUE, "입력값 검증에 실패했습니다"));
    }

    /**
     * 비즈니스 로직 예외 처리
     * 커스텀 예외를 처리합니다.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        log.warn("Business exception occurred: {}", e.getMessage());

        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.error(errorCode, e.getMessage()));
    }

    /**
     * IllegalArgumentException 처리
     * 잘못된 인자가 전달되었을 때 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        log.warn("IllegalArgumentException occurred: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(INVALID_INPUT_VALUE, e.getMessage()));
    }

    /**
     * 기타 모든 예외 처리
     * 예상하지 못한 예외를 처리합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        log.error("Unexpected error occurred", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(INTERNAL_SERVER_ERROR));
    }
}
