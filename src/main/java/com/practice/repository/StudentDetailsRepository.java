/**
 *
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practice.constants.Constants;
import com.practice.constants.QueryConstants;
import com.practice.entity.StudentDetail;

/**
 * @author sulabhtiwari
 *
 */
public interface StudentDetailsRepository extends JpaRepository<StudentDetail, Long> {
	/**
	 *
	 * @param studentId
	 * @return
	 */
	@Query(value = QueryConstants.GET_STUDENTS)
	public StudentDetail getStudent(@Param(Constants.STUDENT_ID) Long studentId);

	@Query(value = QueryConstants.GET_DUPLICATE_STUDENT)
	public StudentDetail getDuplicateStudent(@Param(Constants.STUDENT_ID) Long studentId,
			@Param(Constants.MOBILE_NUMBER) String mobileNumber, @Param(Constants.EMAIL) String email);

}
