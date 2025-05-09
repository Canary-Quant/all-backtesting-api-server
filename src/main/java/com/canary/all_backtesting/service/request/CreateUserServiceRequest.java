package com.canary.all_backtesting.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserServiceRequest {

    private String username;
    private String password;

}
