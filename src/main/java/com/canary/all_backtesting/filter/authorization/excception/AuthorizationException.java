package com.canary.all_backtesting.filter.authorization.excception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends RuntimeException {

    private final AuthorizationErrorCode code;

    public AuthorizationException(AuthorizationErrorCode code) {
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
