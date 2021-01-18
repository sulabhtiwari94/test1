/**
 *
 */
package com.practice.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.practice.vo.validation.StudentMandatoryValidationGroup;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sulabhtiwari
 *
 */
@Getter
@Setter
public class StudentDetailVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long studentId;
	@NotBlank(message = "Address can not be blank.", groups = StudentMandatoryValidationGroup.class)
	private String address;
	@NotBlank(message = "Email can not be blank.", groups = StudentMandatoryValidationGroup.class)
	@Email(message = "Not a valid email.", groups = StudentMandatoryValidationGroup.class)
	private String emailId;
	@NotBlank(message = "Student Name can not be blank.", groups = StudentMandatoryValidationGroup.class)
	@Pattern(regexp = "^[a-zA-z //s]*", message = "First name can have alphabates only.", groups = StudentMandatoryValidationGroup.class)
	private String studentName;
	@Pattern(regexp = "^[0-9]*", message = "mobile number can have numeric only.", groups = StudentMandatoryValidationGroup.class)
	@NotBlank(message = "Mobile Number can not be blank.", groups = StudentMandatoryValidationGroup.class)
	@Size(min = 10, max = 10, message = "Mobile number should have 10 digits only.", groups = StudentMandatoryValidationGroup.class)
	private String mobileNumber;
	private String status;

	private Date updatedDateTime;

	private Date createdDateTime;
	private Integer version;
}
