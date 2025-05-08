package com.canary.all_backtesting.domain.user;

import lombok.Getter;

@Getter
public enum Role {
    BASIC("일반 사용자"),
    ADMIN("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
