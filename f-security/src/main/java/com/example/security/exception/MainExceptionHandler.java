package com.example.security.exception;

import com.example.security.dto.UserErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(UserException.class)
    public UserErrorResponse handleException(
            UserException e, HttpServletRequest request
    ) {
        log.error("errorCode : {}, url : {}, message : {}",
                e.getUserErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return UserErrorResponse.builder()
                .errorCode((e.getUserErrorCode()))
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
