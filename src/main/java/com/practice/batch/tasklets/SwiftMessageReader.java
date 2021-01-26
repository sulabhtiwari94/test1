/**
 *
 */
package com.practice.batch.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class SwiftMessageReader implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		final String message = (String) chunkContext.getStepContext().getJobParameters().get("message");
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("FIN", message);
		return RepeatStatus.FINISHED;
	}

}
