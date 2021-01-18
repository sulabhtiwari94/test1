/**
 *
 */
package com.practice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sulabhtiwari
 *
 */
@Entity
@Table(name = "student_details")
@Getter
@Setter
@EntityListeners(AuditListner.class)
public class StudentDetail implements Serializable, Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "student_id")
	@SequenceGenerator(name = "studentId", sequenceName = "student_details_student_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentId")
	private Long studentId;
	@Column(name = "address")
	private String address;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "student_name")
	private String studentName;

	@Column(name = "mobile_number")
	private String mobileNumber;
	@Embedded
	private Audit audit = new Audit();
	@Column(name = "status")
	private String status;
	@Version
	@Column(name = "version")
	private Integer version;

}
