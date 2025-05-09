package com.canary.all_backtesting.util.jwt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum JwtUtilsErrorCode {

    EXPIRED_ACCESS_TOKEN("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    JWT_UTIL_ERR("jwt token 검증시 오류가 발생했습니다.", HttpStatus.UNAUTHORIZED);


    private final String message;
    private final HttpStatus status;

    JwtUtilsErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
