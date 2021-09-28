package org.example.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    EMPLOYED("재직"),
    RETIRED("퇴직");

    private final String description;
}
