package com.canary.all_backtesting.api.controller.login;

import com.canary.all_backtesting.api.controller.ApiResponse;
import com.canary.all_backtesting.api.controller.login.request.LoginRequest;
import com.canary.all_backtesting.service.user.UserService;
import com.canary.all_backtesting.util.jwt.JwtTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<JwtTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("username = {}, password = {}", request.getUsername(), request.getPassword());
        JwtTokenResponse token = userService.login(request.toServiceRequest());
        return ApiResponse.ok(token);
    }

}
