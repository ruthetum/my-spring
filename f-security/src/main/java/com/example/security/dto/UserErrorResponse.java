package com.example.security.dto;

import com.example.security.exception.UserErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserErrorResponse {
    private UserErrorCode errorCode;
    private String errorMessage;
}