package com.example.housebatch.exception;

import org.springframework.batch.core.JobParametersInvalidException;

public class InvalidFilePathException extends JobParametersInvalidException {

    private static final String message = "가 빈 문자열이거나 올바르지 않습니다.";

    public InvalidFilePathException(String filePath) {
        super(filePath + message);
    }
}
