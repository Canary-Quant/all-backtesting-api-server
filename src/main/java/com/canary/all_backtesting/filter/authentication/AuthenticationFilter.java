package com.canary.all_backtesting.filter.authentication;

import com.canary.all_backtesting.filter.authentication.excception.AuthenticationException;
import com.canary.all_backtesting.util.jwt.JwtUtil;
import io.netty.handler.codec.http.HttpMethod;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

import static com.canary.all_backtesting.filter.authentication.excception.AuthenticationErrorCode.*;

@Slf4j
public class AuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final HashMap<String, String> whiteListMap = new HashMap<>();

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        whiteListMap.put("/users", HttpMethod.POST.name());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.info("authentication filter : header = {}, uri = {}, method = {}", authHeader, requestUri, method);

        if (isWhiteList(requestUri, method)) {
            request.setAttribute("isWhiteList", true);
            filterChain.doFilter(request, response);
            return ;
        }

        if (isAuthHeaderEmpty(authHeader)) {
            throw new AuthenticationException(NOT_FOUND_AUTHORIZATION_HEADER);
        }

        String[] contents = authHeader.split(" ");
        if (!isValidAuthHeaderFormat(contents)) {
            throw new AuthenticationException(INVALID_AUTHORIZATION_HEADER_FORMAT);
        }

        String accessToken = contents[1];
        jwtUtil.validateToken(accessToken);

        request.setAttribute("username", jwtUtil.getUserName(accessToken));
        request.setAttribute("role", jwtUtil.getUserRole(accessToken));

        filterChain.doFilter(request, response);
    }

    private boolean isAuthHeaderEmpty(String header) {
        return header == null;
    }

    private boolean isValidAuthHeaderFormat(String[] contents) {
        if (contents.length != 2) {
            return false;
        }

        String bearer = contents[0];
        String token = contents[1];

        return bearer.equals("Bearer") && !token.isBlank();

    }

    private boolean isWhiteList(String uri, String method) {
         return whiteListMap.containsKey(uri) && whiteListMap.get(uri).equals(method);
    }
}
