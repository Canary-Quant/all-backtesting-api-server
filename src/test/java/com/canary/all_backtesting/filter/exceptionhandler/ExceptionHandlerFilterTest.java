package com.canary.all_backtesting.filter.exceptionhandler;

import com.canary.all_backtesting.filter.authentication.excception.AuthenticationErrorCode;
import com.canary.all_backtesting.filter.authentication.excception.AuthenticationException;
import com.canary.all_backtesting.filter.authorization.excception.AuthorizationErrorCode;
import com.canary.all_backtesting.filter.authorization.excception.AuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.canary.all_backtesting.filter.authentication.excception.AuthenticationErrorCode.*;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerFilterTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    ExceptionHandlerFilter exceptionHandlerFilter;

    @DisplayName("인증 오류가 발생할 경우 401 응답을 반환한다.")
    @Test
    void handleAuthenticationException() throws ServletException, IOException {

        Mockito.doThrow(new AuthenticationException(NOT_FOUND_AUTHORIZATION_HEADER))
                .when(filterChain)
                .doFilter(request, response);

        exceptionHandlerFilter.doFilter(request, response, filterChain);

        Mockito.verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        Mockito.verify(response).setContentType("application/json");
    }

    @DisplayName("인가 오류가 발생할 경우 403 응답을 반환한다.")
    @Test
    void handleAuthorizationException() throws ServletException, IOException {

        Mockito.doThrow(new AuthorizationException(AuthorizationErrorCode.AUTHORIZATION_ERROR_CODE))
                .when(filterChain)
                .doFilter(request, response);

        exceptionHandlerFilter.doFilter(request, response, filterChain);

        Mockito.verify(response).setStatus(HttpStatus.FORBIDDEN.value());
        Mockito.verify(response).setContentType("application/json");
    }
}