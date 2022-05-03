package com.example.housebatch.job.lawd;

import com.example.housebatch.core.entity.Lawd;
import com.example.housebatch.core.service.LawdService;
import com.example.housebatch.job.validator.FilePathParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LawdInsertJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final LawdService lawdService;

    private static final String LAWD_INSERT_JOB = "lawdInsertJob";
    private static final String LAWD_INSERT_STEP = "lawdInsertStep";
    private static final String LAWD_FLAT_FILE_ITEM_READER = "lawdFlatFileItemReader";
    private static final int CHUNK_SIZE = 1000;
    private static final String DELIMITER = "\t";

    @Bean
    public Job lawdInsertJob(Step lawdInsertStep) {
        return jobBuilderFactory.get(LAWD_INSERT_JOB)
                .incrementer(new RunIdIncrementer())
                .validator(new FilePathParameterValidator())
                .start(lawdInsertStep)
                .build();
    }

    @JobScope
    @Bean
    public Step lawdInsertStep(
            FlatFileItemReader<Lawd> lawdFlatFileItemReader,
            ItemWriter<Lawd> lawdItemWriter
    ) {
        return stepBuilderFactory.get(LAWD_INSERT_STEP)
                .<Lawd, Lawd>chunk(CHUNK_SIZE)
                .reader(lawdFlatFileItemReader)
                .writer(lawdItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<Lawd> lawdFlatFileItemReader(
            @Value("#{jobParameters['filePath']}") String filePath
    ) {
        return new FlatFileItemReaderBuilder<Lawd>()
                .name(LAWD_FLAT_FILE_ITEM_READER)
                .delimited()
                .delimiter(DELIMITER)
                .names(LawdFieldSetMapper.LAWD_CD, LawdFieldSetMapper.LAWD_DONG, LawdFieldSetMapper.EXIST)
                .linesToSkip(1)
                .fieldSetMapper(new LawdFieldSetMapper())
                .resource(new ClassPathResource(filePath))
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Lawd> lawdItemWriter() {
        return items -> items.forEach(lawdService::upsert);
    }
}
