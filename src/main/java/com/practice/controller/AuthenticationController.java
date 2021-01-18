/**
 *
 */
package com.practice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.vo.ResponseVO;

/**
 * @author sulabhtiwari
 *
 */
@RestController
@RequestMapping(value = "/service/login")
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	private final HttpHeaders headers = new HttpHeaders();
	private static final String APPLICATION_JSON = "application/json";

	@RequestMapping(value = "/authenticate_user", method = RequestMethod.GET, produces = APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<ResponseVO<String>> addStudent() {
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(new ResponseVO<>("User Logged in successfully", "User Logged in successfully"),
				headers, HttpStatus.OK);
	}
}
