/**
 *
 */
package com.practice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.jms.JMSProducer;
import com.practice.payment.message.PaymentMessageWriter;
import com.practice.service.StudentDetailsService;
import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
@RestController
@RequestMapping(value = "/service/student_details")
public class StudentDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsController.class);
	@Autowired
	private JobLauncher jobLauncher;
	@Qualifier("studentJob")
	@Autowired
	private Job job;
	@Autowired
	private StudentDetailsService studentDeatialsService;
	@Autowired
	private JMSProducer producer;
	@Autowired
	private PaymentMessageWriter paymentMessageWriter;
	private final HttpHeaders headers = new HttpHeaders();
	private static final String APPLICATION_JSON = "application/json";

	@Qualifier("jobOperator")
	@Autowired
	private JobOperator jobOperator;

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<ResponseVO<StudentDetailVO>> addStudent(@RequestBody StudentDetailVO studentDetailVO) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		final ResponseVO<StudentDetailVO> responseVO = studentDeatialsService.insertStudent(studentDetailVO);
		return new ResponseEntity<>(responseVO, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<ResponseVO<StudentDetailVO>> updateStudent(@RequestBody List<StudentDetailVO> students) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		final ResponseVO<StudentDetailVO> responseVO = studentDeatialsService.updateStudents(students);
		return new ResponseEntity<>(responseVO, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<ResponseVO<StudentDetailVO>> deleteStudent(@RequestBody List<StudentDetailVO> students) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		final ResponseVO<StudentDetailVO> responseVO = studentDeatialsService.deleteStudents(students);
		return new ResponseEntity<>(responseVO, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<ResponseVO<StudentDetailVO>> getStudent(@RequestBody StudentDetailVO studentDetailVO) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		final ResponseVO<StudentDetailVO> responseVO = studentDeatialsService.inquireStudents(studentDetailVO);
		return new ResponseEntity<>(responseVO, headers, HttpStatus.OK);
	}

	@GetMapping(value = "/loadFile", produces = APPLICATION_JSON)
	public ResponseEntity<ResponseVO<String>> load() throws JobParametersInvalidException,
			JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		headers.setContentType(MediaType.APPLICATION_JSON);

		final JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.addString("jobName", "LoadFile").toJobParameters();
		final JobExecution jobExecution = jobLauncher.run(job, parameters);

		LOGGER.info("JobExecution: " + jobExecution.getStatus());

		LOGGER.info("Batch is Running...");
		while (jobExecution.isRunning()) {
			LOGGER.info("...");
		}
		LOGGER.info(jobExecution.getStatus().name());
		return new ResponseEntity<>(new ResponseVO<>(jobExecution.getStatus().name(), ""), headers, HttpStatus.OK);
	}

	@GetMapping(value = "/jms", produces = APPLICATION_JSON)
	public String sentPaymentMessage() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {

		final String message = paymentMessageWriter.createMessage();
		producer.sendMessage(message);
		return message;
	}

	@GetMapping(value = "/restartJob/{id}", produces = APPLICATION_JSON)
	public Long restartJob(@PathVariable("id") long id)
			throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException {

		return jobOperator.restart(id);
	}
}
