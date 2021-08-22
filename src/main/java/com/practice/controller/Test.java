/**
 *
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author sulabhtiwari
 *
 */
public class Test {

	@Value("${query.insert}")
	String test;

	public void insert1() {
		System.out.println("one>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + test);
	}

}
