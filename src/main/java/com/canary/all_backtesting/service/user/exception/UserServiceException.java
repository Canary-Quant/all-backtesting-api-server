package com.canary.all_backtesting.service.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserServiceException extends RuntimeException {

    private final UserServiceErrorCode code;

    public UserServiceException(UserServiceErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getStatusCode() {
        return code.getStatus();
    }

    public int getStatusCodeValue() {
        return code.getStatus().value();
    }
}
