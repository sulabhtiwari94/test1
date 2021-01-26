/**
 *
 */
package com.practice.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class JMSReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(JMSReceiver.class);
	@Autowired
	private JobLauncher jobLauncher;
	@Qualifier("paymentProcessingJob")
	@Autowired
	private Job job;

	@JmsListener(destination = "${student.transaction.queue}")
	public void receiveMessage(String message) {
		LOGGER.info("Message Received===>" + message);
		final JobParameters jobParameters = new JobParametersBuilder().addString("message", message).toJobParameters();
		try {
			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
