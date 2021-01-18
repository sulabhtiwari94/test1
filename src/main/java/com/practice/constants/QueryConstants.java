/**
 *
 */
package com.practice.constants;

/**
 * @author sulabhtiwari
 *
 */
public final class QueryConstants {

	public static final String GET_STUDENTS = "Select st from StudentDetail st where st.studentId=:STUDENT_ID AND st.status='ACTIVE' ";
	public static final String GET_DUPLICATE_STUDENT = "Select st from StudentDetail st where st.studentId!=:STUDENT_ID AND (st.mobileNumber=:MOBILE_NUMBER OR LOWER(st.emailId)=LOWER(:EMAIL)) AND st.status='ACTIVE'";

	private QueryConstants() throws IllegalAccessException {
		throw new IllegalAccessException("Can not call private constructor");
	}
}
