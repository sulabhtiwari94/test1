/**
 *
 */
package com.practice.batch.tasklets;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.payment.message.PaymentMessageParser;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class SwiftMessageProcessor implements Tasklet, StepExecutionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SwiftMessageProcessor.class);
	@Autowired
	private PaymentMessageParser paymentMessageParser;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		final MT103 mt103 = paymentMessageParser.parseMessage((String) chunkContext.getStepContext().getStepExecution()
				.getJobExecution().getExecutionContext().get("FIN"));
		LOGGER.info("Received amount is>>>>" + mt103.getField32A().getAmount());
		if (new BigDecimal(mt103.getField32A().getAmount()).compareTo(new BigDecimal(1000)) < 0) {
			contribution.setExitStatus(ExitStatus.FAILED);
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}

}
