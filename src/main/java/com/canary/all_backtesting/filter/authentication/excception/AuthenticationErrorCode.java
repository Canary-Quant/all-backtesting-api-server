package com.canary.all_backtesting.filter.authentication.excception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthenticationErrorCode {
    NOT_FOUND_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, "Authorization 으로 시작하는 헤더가 없습니다."),
    INVALID_AUTHORIZATION_HEADER_FORMAT(HttpStatus.UNAUTHORIZED, "값의 형식은 Bearer XXXXXX 입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;

    AuthenticationErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
