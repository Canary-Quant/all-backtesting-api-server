package com.canary.all_backtesting.service.upbit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UpBitServiceException extends RuntimeException {

    private final UpBitServiceErrorCode code;

    public UpBitServiceException(UpBitServiceErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return code.getStatus();
    }

    public int getHttpStatusValue() {
        return code.getStatus().value();
    }
}
