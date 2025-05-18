package com.canary.all_backtesting.api.controller.user;

import com.canary.all_backtesting.api.controller.ApiResponse;
import com.canary.all_backtesting.api.controller.user.request.CreateUserRequest;
import com.canary.all_backtesting.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Void> join(@Valid CreateUserRequest request) {
        userService.join(request.toServiceRequest());
        return ApiResponse.ok(null);
    }

}
