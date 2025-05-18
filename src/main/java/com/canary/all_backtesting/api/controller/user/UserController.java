package com.canary.all_backtesting.api.controller.user;

import com.canary.all_backtesting.api.controller.ApiResponse;
import com.canary.all_backtesting.api.controller.user.request.CreateUserRequest;
import com.canary.all_backtesting.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<String> join(@Valid @RequestBody CreateUserRequest request) {
        log.info("userController.login: 회원 가입 요청 username = {}, password = {}", request.getUsername(), request.getPassword());
        userService.join(request.toServiceRequest());
        log.info("userController.login: 회원 가입 성공");
        return ApiResponse.ok("회원가입 성공");
    }

}
