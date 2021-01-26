/**
 *
 */
package com.practice.batch.steps;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class StudentFileReader extends FlatFileItemReader<StudentDetailVO> {

	private final String filePath = "src/main/resources/students.csv";

	public StudentFileReader() {
		this.setResource(new FileSystemResource(filePath));
		this.setName("CSV-Reader");
		this.setLinesToSkip(1);
		this.setLineMapper(lineMapper());
	}

	private LineMapper<StudentDetailVO> lineMapper() {

		final DefaultLineMapper<StudentDetailVO> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "studentName", "mobileNumber", "emailId", "address" });

		final BeanWrapperFieldSetMapper<StudentDetailVO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(StudentDetailVO.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}
}
