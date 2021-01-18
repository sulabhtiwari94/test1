/**
 *
 */
package com.practice.constants;

/**
 * @author sulabhtiwari
 *
 */
public final class Constants {

	public static final String EMPTY_STRING = "";
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String STUDENT_ID = "STUDENT_ID";
	public static final String STUDENT_NAME = "STUDENT_NAME";
	public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
	public static final String EMAIL = "EMAIL";

	private Constants() throws IllegalAccessException {
		throw new IllegalAccessException("Can not call private constructor");
	}
}
