/**
 *
 */
package com.practice.dao;

import java.util.List;

import com.practice.entity.StudentDetail;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
public interface StudentDao {
	/**
	 *
	 * @param studentDetailVO
	 * @return
	 */
	List<StudentDetail> inquireStudents(StudentDetailVO studentDetailVO);

	/**
	 * 
	 * @param detail
	 */
	void updateStudent(StudentDetail detail);
}
