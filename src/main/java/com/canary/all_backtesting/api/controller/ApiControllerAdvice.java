package com.canary.all_backtesting.api.controller;

import com.canary.all_backtesting.service.user.exception.UserServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ApiResponse<String> handleBindingResult(BindException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.unAuthorized(message);
    }

    @ExceptionHandler(UserServiceException.class)
    public ApiResponse<String> handleUserServiceException(UserServiceException e) {
        return ApiResponse.unAuthorized(e.getMessage());
    }
}
