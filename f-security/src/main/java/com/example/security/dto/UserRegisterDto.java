package com.example.security.dto;

import com.sun.istack.NotNull;
import lombok.*;

public class UserRegisterDto {

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class Request {

        @NotNull
        private String username;
        @NotNull
        private String password;
    }
}
