package com.canary.all_backtesting.service.upbit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UpBitServiceErrorCode {
    UPBIT_SERVER_ERROR("upbit server 내부 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UPBIT_CLIENT_ERROR("client 오류 입니다. 요청 스펙을 다시 확인해 주세요.", HttpStatus.BAD_REQUEST),
    UNEXPECTED_ERROR("예기치 못한 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    UpBitServiceErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
