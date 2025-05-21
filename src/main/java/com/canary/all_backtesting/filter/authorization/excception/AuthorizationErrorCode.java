package com.canary.all_backtesting.filter.authorization.excception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthorizationErrorCode {
    AUTHORIZATION_ERROR_CODE(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;

    AuthorizationErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
