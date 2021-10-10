package com.example.security.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Authority {
    ROLE_ADMIN("관리자"),
    ROLE_USER("사용자");

    private final String description;
}
