package com.canary.all_backtesting.api.controller.login.request;

import com.canary.all_backtesting.service.user.request.LoginServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "id 는 필수 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginServiceRequest toServiceRequest() {
        return new LoginServiceRequest(username, password, System.currentTimeMillis());
    }
}
