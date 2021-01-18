package com.practice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.practice.batch.steps.StudentFileReader;
import com.practice.batch.steps.StudentProcessor;
import com.practice.batch.steps.StudentWriter;
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

	@Bean()
	protected Step jobSteps() {
		return steps.get("process student").<StudentDetailVO, ResponseVO<StudentDetailVO>>chunk(2).reader(reader)
				.processor(processor).writer(writer).faultTolerant().skipLimit(10).skip(StudentTechnicalException.class)
				.build();
	}

	@Bean()
	public Job studentJob() {
		return jobs.get("studentJob").incrementer(new RunIdIncrementer()).start(jobSteps()).build();
	}

}
