package com.canary.all_backtesting.api.controller.user.request;

import com.canary.all_backtesting.service.user.request.CreateUserServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "id 값은 필수 입니다.")
    private String username;

    @NotNull(message = "password 는 필수 입니다.")
    private String password;

    public CreateUserServiceRequest toServiceRequest() {
        return new CreateUserServiceRequest(username, password);
    }

}
