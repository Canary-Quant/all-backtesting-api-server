package com.canary.all_backtesting.util.jwt.exception;

import org.springframework.http.HttpStatus;

public class JwtUtilsException extends RuntimeException {

    private JwtUtilsErrorCode code;

    public JwtUtilsException(JwtUtilsErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getStatus() {
      return code.getStatus();
    }
}
