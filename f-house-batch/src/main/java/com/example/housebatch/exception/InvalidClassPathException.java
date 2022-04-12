package com.example.housebatch.exception;

import org.springframework.batch.core.JobParametersInvalidException;

public class InvalidClassPathException extends JobParametersInvalidException {

    private static final String message = "가 ClassPath에 존재하지 않습니다.";

    public InvalidClassPathException(String filePath) {
        super(filePath + message);
    }
}
