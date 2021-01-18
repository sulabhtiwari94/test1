/**
 *
 */
package com.practice.service;

import java.util.List;

import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
public interface StudentDetailsService {
	/**
	 *
	 * @param studentDetailVO
	 * @return
	 */

	public ResponseVO<StudentDetailVO> insertStudent(StudentDetailVO studentDetailVO);

	/**
	 *
	 * @param studentDetailVO
	 * @return
	 */
	public ResponseVO<StudentDetailVO> inquireStudents(StudentDetailVO studentDetailVO);

	/**
	 *
	 * @param students
	 * @return
	 */

	public ResponseVO<StudentDetailVO> updateStudents(List<StudentDetailVO> students);

	/**
	 *
	 * @param students
	 * @return
	 */

	public ResponseVO<StudentDetailVO> deleteStudents(List<StudentDetailVO> students);

}
