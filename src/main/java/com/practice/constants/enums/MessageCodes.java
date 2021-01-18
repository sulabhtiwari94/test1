/**
 *
 */
package com.practice.constants.enums;

/**
 * @author sulabhtiwari
 *
 */
public enum MessageCodes {

	STUDENT_ACTION("APP000001F"), STUDENT_ACTION_FAILED("APP000002F"), NO_STUDENT_PRESENT("APP000003F"),
	STUDENT_VERSION_MISMATCH("APP000004F"), STUDENT_MULTI_ATION("APP000005F"), DUPLICATE_STUDENTS("APP000006F"),
	STIDENT_TECHNICAL_FAILURE("APP000001T");

	private String messageCode;

	private MessageCodes(String messageCode) {
		this.messageCode = messageCode;

	}

	public String getMessageCode() {
		return messageCode;
	}
}
