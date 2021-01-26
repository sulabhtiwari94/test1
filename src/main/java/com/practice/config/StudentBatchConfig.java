package com.practice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.practice.batch.steps.StudentFileReader;
import com.practice.batch.steps.StudentProcessor;
import com.practice.batch.steps.StudentWriter;
import com.practice.batch.tasklets.SwiftMessageProcessor;
import com.practice.batch.tasklets.SwiftMessageReader;
import com.practice.exception.StudentTechnicalException;
import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;

@Configuration
@EnableBatchProcessing
public class StudentBatchConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private StudentProcessor processor;
	@Autowired
	private StudentWriter writer;
	@Autowired
	private StudentFileReader reader;
	@Autowired
	private SwiftMessageReader messageReader;
	@Autowired
	private SwiftMessageProcessor swiftMessageProcessor;

	@Bean()
	protected Step jobSteps() {
		return steps.get("process student").<StudentDetailVO, ResponseVO<StudentDetailVO>>chunk(2).reader(reader)
				.processor(processor).writer(writer).faultTolerant().skipLimit(10).skip(StudentTechnicalException.class)
				.build();
	}

	@Bean
	public JobOperator jobOperator(final JobLauncher jobLauncher, final JobRepository jobRepository,
			final JobRegistry jobRegistry, final JobExplorer jobExplorer) {
		final SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobLauncher(jobLauncher);
		jobOperator.setJobRepository(jobRepository);
		jobOperator.setJobRegistry(jobRegistry);
		jobOperator.setJobExplorer(jobExplorer);
		return jobOperator;
	}

	@Bean()
	public Job studentJob() {
		return jobs.get("studentJob").incrementer(new RunIdIncrementer()).start(jobSteps()).build();
	}

	@Bean()
	protected Step messageReaderStep() {
		return steps.get("read payment message").tasklet(messageReader).allowStartIfComplete(false).build();
	}

	@Bean()
	protected Step messageProcessorStep() {
		return steps.get("process payment message").tasklet(swiftMessageProcessor).allowStartIfComplete(false).build();
	}

	@Bean()
	public Job paymentProcessingJob() {
		return jobs.get("paymentProcessingJob").incrementer(new RunIdIncrementer()).start(messageReaderStep())
				.next(messageProcessorStep()).build();
	}
}
