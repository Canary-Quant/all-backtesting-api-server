package com.canary.all_backtesting.service.user.exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends RuntimeException {

    private final UserServiceErrorCode code;

    public UserServiceException(UserServiceErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getStatusCode() {
        return code.getStatus();
    }
}
