package com.canary.all_backtesting.filter.authentication;

import com.canary.all_backtesting.filter.authentication.excception.AuthenticationException;
import com.canary.all_backtesting.util.jwt.JwtUtil;
import io.netty.handler.codec.http.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    AuthenticationFilter authenticationFilter;

    @DisplayName("인증에 성공할 경우 다음 filter 로 넘어간다.")
    @Test
    void doFilter() throws Exception {
        Mockito.when(request.getHeader(any()))
                .thenReturn("Bearer awiqwndodqqwdqwqwdqwdqwwd");

        Mockito.when(request.getRequestURI())
                .thenReturn("/no-white-list");

        Mockito.doNothing().when(jwtUtil).validateToken(any());

        authenticationFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain, Mockito.atLeastOnce()).doFilter(request, response);
    }

    @DisplayName("Authorization header 가 없을 경우 예외를 발생 시킨다.")
    @Test
    void emptyAuthorizationHeader() throws ServletException, IOException {
        Mockito.when(request.getHeader(any(String.class)))
                .thenReturn(null);

        Mockito.when(request.getRequestURI())
                .thenReturn("/no-white-list");


        assertThatThrownBy(() -> authenticationFilter.doFilter(request, response, filterChain))
                .isInstanceOf(AuthenticationException.class);

        Mockito.verify(filterChain, Mockito.times(0)).doFilter(request, response);
    }

    @DisplayName("key 값이 Bearer XXX 형식이 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Bearer ", "Bea qwdqwdqdw", "wdidqqidwqd"})
    void invalidHeaderFormat(String headerValue) throws ServletException, IOException {
        Mockito.when(request.getHeader(any(String.class)))
                .thenReturn(headerValue);

        Mockito.when(request.getRequestURI())
                .thenReturn("/no-white-list");

        assertThatThrownBy(() -> authenticationFilter.doFilter(request, response, filterChain))
                .isInstanceOf(AuthenticationException.class);

        Mockito.verify(filterChain, Mockito.times(0)).doFilter(request, response);
    }

    @DisplayName("white list 에 등록된 경로일 경우 다음 filter 로 넘어간다.")
    @Test
    void whiteList() throws ServletException, IOException {
        Mockito.when(request.getHeader(any(String.class)))
                .thenReturn("Bearer tesswsssst");

        Mockito.when(request.getRequestURI())
                .thenReturn("/users");

        Mockito.when(request.getMethod())
                .thenReturn(HttpMethod.POST.name());

        authenticationFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }
}