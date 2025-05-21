package com.canary.all_backtesting.filter.authentication.excception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends RuntimeException {

    private final AuthenticationErrorCode code;

    public AuthenticationException(AuthenticationErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return code.getHttpStatus();
    }

    public int getHttpStatusValue() {
        return code.getHttpStatus().value();
    }
}
