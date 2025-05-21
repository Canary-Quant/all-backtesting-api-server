package com.canary.all_backtesting.filter.exceptionhandler;

import com.canary.all_backtesting.filter.authentication.excception.AuthenticationException;
import com.canary.all_backtesting.filter.authorization.excception.AuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ExceptionHandlerFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            handleAuthenticationException(response, e);
        } catch (AuthorizationException e) {
            handleAuthorizationException(response, e);
        }
    }

    private void handleAuthenticationException(HttpServletResponse response,AuthenticationException e) {
        String data = e.getMessage();
        response.setStatus(e.getHttpStatusValue());
        response.setContentType("application/json");
        writeResponse(response, data);
    }

    private void handleAuthorizationException(HttpServletResponse response, AuthorizationException e) {
        String data = e.getMessage();
        response.setStatus(e.getHttpStatusValue());
        response.setContentType("application/json");
        writeResponse(response, data);
    }

    private void writeResponse(HttpServletResponse response, String data) {
        try {
            String content = objectMapper.writeValueAsString(data);
            response.getWriter().write(content);
        } catch (Exception e) {
            log.error("exception handler filter: 응답 생성시 오류");
        }
    }
}
