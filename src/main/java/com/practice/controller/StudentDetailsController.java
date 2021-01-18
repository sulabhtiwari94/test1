/**
 *
 */
package com.practice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	private final HttpHeaders headers = new HttpHeaders();
	private static final String APPLICATION_JSON = "application/json";

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
		final Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		final JobParameters parameters = new JobParameters(maps);
		final JobExecution jobExecution = jobLauncher.run(job, parameters);

		LOGGER.info("JobExecution: " + jobExecution.getStatus());

		LOGGER.info("Batch is Running...");
		while (jobExecution.isRunning()) {
			LOGGER.info("...");
		}
		LOGGER.info(jobExecution.getStatus().name());
		return new ResponseEntity<>(new ResponseVO<>(jobExecution.getStatus().name(), ""), headers, HttpStatus.OK);
	}
}
