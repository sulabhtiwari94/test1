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
@Table(name = "college")
@Getter
@Setter
@EntityListeners(AuditListner.class)
public class College implements Serializable, Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "college_id")
	@SequenceGenerator(name = "collegeId", sequenceName = "college_college_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collegeId")
	private Long collegeId;

//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "college")
//	@Column(name = "students")
//	@Cascade(CascadeType.SAVE_UPDATE)
//	private Set<StudentDetail> studentDetail = new HashSet<>(0);

	@Embedded
	private Audit audit = new Audit();
	@Version
	@Column(name = "version")
	private Integer version;

}
