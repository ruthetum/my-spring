package com.example.housebatch.job.validator;

import com.example.housebatch.exception.InvalidClassPathException;
import com.example.housebatch.exception.InvalidFilePathException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class FilePathParameterValidator implements JobParametersValidator {

    private static final String FILE_PATH = "filePath";

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String filePath = parameters.getString(FILE_PATH);
        if (!StringUtils.hasText(filePath))
            throw new InvalidFilePathException(FILE_PATH);

        Resource resource = new ClassPathResource(filePath);
        if (!resource.exists())
            throw new InvalidClassPathException(FILE_PATH);
    }
}
