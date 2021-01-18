/**
 *
 */
package com.practice.entity;

import java.sql.Timestamp;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.practice.util.DateUtil;

/**
 * @author sulabhtiwari
 *
 */
public class AuditListner {

	@PrePersist
	public void setCreatedOn(Auditable auditable) {
		final Audit audit = auditable.getAudit();
		audit.setCreatedDateTime(new Timestamp(DateUtil.getCurrentUTCTime().getTime()));
	}

	@PreUpdate
	public void setUpdateOn(Auditable auditable) {
		final Audit audit = auditable.getAudit();
		audit.setUpdatedDateTime(new Timestamp(DateUtil.getCurrentUTCTime().getTime()));
	}

}
