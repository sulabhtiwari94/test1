/**
 *
 */
package com.practice.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author sulabhtiwari
 *
 */
public class AbstractDao {
	@PersistenceContext
	public EntityManager entityManager;

}
