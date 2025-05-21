package com.canary.all_backtesting.filter.authorization;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.filter.authorization.excception.AuthorizationErrorCode;
import com.canary.all_backtesting.filter.authorization.excception.AuthorizationException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.canary.all_backtesting.filter.authorization.excception.AuthorizationErrorCode.*;

@Slf4j
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain) ;
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ((boolean) request.getAttribute("isWhiteList")) {
            chain.doFilter(request, response);
            return ;
        }

        String requestUri = request.getRequestURI();
        String username = (String) request.getAttribute("username");
        Role role = (Role) request.getAttribute("role");

        log.info("authorizationFilter.doFilter: username = {}, role = {}, request uri = {}", username, role, requestUri);

        if (requestUri.startsWith("/admin") && role != Role.ADMIN) {
            throw new AuthorizationException(AUTHORIZATION_ERROR_CODE);
        }

        chain.doFilter(request, response);
    }
}
