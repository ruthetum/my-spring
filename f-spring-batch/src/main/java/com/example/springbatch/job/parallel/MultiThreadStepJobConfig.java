package com.example.springbatch.job.parallel;

import com.example.springbatch.dto.AmountDto;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.File;
import java.io.IOException;

/**
 * Multi-threaded Step (single process)
 * 단일 프로세스에서 청크 단위로 작업
 */
@Configuration
@AllArgsConstructor
public class MultiThreadStepJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiThreadStepJob(Step multiThreadStep) {
        return jobBuilderFactory.get("multiThreadStepJob")
                .incrementer(new RunIdIncrementer())
                .start(multiThreadStep)
                .build();
    }

    @JobScope
    @Bean
    public Step multiThreadStep(
            FlatFileItemReader<AmountDto> amountFileItemReader,
            ItemProcessor<AmountDto, AmountDto> amountFileItemProcessor,
            FlatFileItemWriter<AmountDto> amountFileItemWriter,
            TaskExecutor multiThreadStepTaskExecutor
    ) {
        return stepBuilderFactory.get("multiThreadStep")
                .<AmountDto, AmountDto>chunk(10)
                .reader(amountFileItemReader)
                .processor(amountFileItemProcessor)
                .writer(amountFileItemWriter)
                .taskExecutor(multiThreadStepTaskExecutor)
                .build();
    }

    @Bean
    public TaskExecutor multiThreadStepTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring-batch-task-executor");
        taskExecutor.setConcurrencyLimit(4);
        return taskExecutor;
    }

    @StepScope
    @Bean
    public FlatFileItemReader<AmountDto> amountFileItemReader() {
        return new FlatFileItemReaderBuilder<AmountDto>()
                .name("amountFileItemReader")
                .lineTokenizer(new DelimitedLineTokenizer())
                .fieldSetMapper(new AmountFieldSetMapper())
                .resource(new FileSystemResource("data/input.txt"))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<AmountDto, AmountDto> amountFileItemProcessor() {
        return item -> {
            item.setAmount(item.getAmount() * 100);
            return item;
        };
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<AmountDto> amountFileItemWriter() throws IOException {
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(new String[] {"index", "name", "amount"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator lineAggregator = new DelimitedLineAggregator();
        lineAggregator.setFieldExtractor(fieldExtractor);

        new File("data/output.txt").createNewFile();
        FileSystemResource resource = new FileSystemResource("data/output.txt");

        return new FlatFileItemWriterBuilder<AmountDto>()
                .name("amountFileItemWriter")
                .resource(resource)
                .lineAggregator(lineAggregator)
                .build();
    }
}
