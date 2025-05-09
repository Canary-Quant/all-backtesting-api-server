package com.canary.all_backtesting.service.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserServiceErrorCode {

    NOT_FOUND_USER("해당 사용자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_USERNAME("중복되는 아이디 입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED)
    ;

    private final String message;
    private final HttpStatus status;

    UserServiceErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status =  status;
    }
}
