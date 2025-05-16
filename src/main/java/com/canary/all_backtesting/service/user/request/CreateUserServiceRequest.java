package com.canary.all_backtesting.service.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserServiceRequest {

    private String username;
    private String password;

}
