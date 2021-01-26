/**
 *
 */
package com.practice.batch.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.practice.constants.Constants;
import com.practice.exception.StudentTechnicalException;
import com.practice.service.impl.StudentDetailsServiceImpl;
import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;
import com.practice.vo.validation.StudentValidator;

/**
 * @author sulabhtiwari
 */
@Component
@StepScope
public class StudentProcessor implements ItemProcessor<StudentDetailVO, ResponseVO<StudentDetailVO>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentProcessor.class);
	@Autowired
	private StudentDetailsServiceImpl studentDeatialsServiceImpl;
	@Autowired
	private StudentValidator studentValidator;
	@Value("#{jobParameters['jobName']}")
	private String jobName;

	@Override
	public ResponseVO<StudentDetailVO> process(StudentDetailVO item) throws Exception {

		final String msg = studentValidator.performTechnicalValidation(item).toString();
		LOGGER.info(jobName);
		if (msg.length() > 0) {
			LOGGER.error(msg);
			throw new StudentTechnicalException(msg);
		}
		item.setStatus(Constants.ACTIVE);
		return studentDeatialsServiceImpl.insertStudent(item);
	}

}
