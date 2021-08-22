/**
 *
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.College;

/**
 * @author sulabhtiwari
 *
 */
public interface CollegeRepository extends JpaRepository<College, Long> {

}
