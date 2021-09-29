package org.example.dmaker.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.dmaker.dto.DmakerErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.example.dmaker.exception.DmakerErrorCode.*;

@Slf4j
@RestControllerAdvice
public class DmakerExceptionHandler {

    @ExceptionHandler(DmakerException.class)
    public DmakerErrorResponse handleException(
            DmakerException e, HttpServletRequest request
    ) {
        log.error("errorCode : {}, url : {}, message : {}",
                e.getDmakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DmakerErrorResponse.builder()
                .errorCode((e.getDmakerErrorCode()))
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DmakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ) {
        log.error("url : {}, message : {}",
                request.getRequestURI(), e.getMessage());

        return DmakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public DmakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ) {
        log.error("url : {}, message : {}",
                request.getRequestURI(), e.getMessage());

        return DmakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
