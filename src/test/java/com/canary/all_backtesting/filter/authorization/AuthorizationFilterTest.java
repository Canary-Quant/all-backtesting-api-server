package com.canary.all_backtesting.filter.authorization;

import com.canary.all_backtesting.domain.user.Role;
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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class AuthorizationFilterTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @DisplayName("white list 에 등록된 경로일 경우 다음 filter 를 호출한다.")
    @Test
    void authorization() throws ServletException, IOException {

        Mockito.when(request.getAttribute("isWhiteList"))
                        .thenReturn(true);

        authorizationFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }

    @DisplayName("관리자가 admin 경로를 호출 할 경우 다음 filter 를 호출한다.")
    @Test
    void adminPathWithAdminUser() throws ServletException, IOException {
        Mockito.when(request.getAttribute("isWhiteList"))
                .thenReturn(false);

        Mockito.when( request.getRequestURI())
                .thenReturn("/admin/other");

        Mockito.when( request.getAttribute("username"))
                .thenReturn("test");

        Mockito.when(request.getAttribute("role"))
                .thenReturn(Role.ADMIN);

        authorizationFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }

    @DisplayName("사용자가 admin 경로를 호출 할 경우 다음 filter 를 호출한다.")
    @Test
    void adminPathWithBasicUser() throws ServletException, IOException {
        Mockito.when(request.getAttribute("isWhiteList"))
                .thenReturn(false);

        Mockito.when( request.getRequestURI())
                .thenReturn("/admin/other");

        Mockito.when( request.getAttribute("username"))
                .thenReturn("test");

        Mockito.when(request.getAttribute("role"))
                .thenReturn(Role.BASIC);

        assertThatThrownBy(() -> authorizationFilter.doFilter(request, response, filterChain))
                .isInstanceOf(AuthorizationException.class);
        Mockito.verify(filterChain, Mockito.times(0)).doFilter(request, response);
    }

}