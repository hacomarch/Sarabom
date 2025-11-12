package com.example.sarabom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 공통
    SUCCESS("200", "OK")
    ;

    private final String code;
    private final String message;
}
