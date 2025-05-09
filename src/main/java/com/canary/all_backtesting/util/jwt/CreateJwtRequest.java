package com.canary.all_backtesting.util.jwt;

import com.canary.all_backtesting.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJwtRequest {
    private String username;
    private Role role;
    private Long nowMS;
}
