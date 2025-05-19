package com.canary.all_backtesting.api.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Data
public class ApiResponse<T> {

    private HttpStatus status;
    private int code;
    private T data;

    public ApiResponse(HttpStatus status, int code, T data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, int code, T data) {
        return new ApiResponse<>(status, code, data);
    }

    public static <T> ApiResponse<T> unAuthorized(T data) {
        return ApiResponse.of(UNAUTHORIZED, UNAUTHORIZED.value(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.of(OK, OK.value(), data);
    }

    public static <T> ApiResponse<T> ok() {
        return ApiResponse.of(OK, OK.value(), null);
    }

}
