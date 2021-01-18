/**
 *
 */
package com.practice.entity;

/**
 * @author sulabhtiwari
 *
 */
public interface Auditable {

	Audit getAudit();

	void setAudit(Audit audit);

}
