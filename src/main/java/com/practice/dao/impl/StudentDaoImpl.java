/**
 *
 */
package com.practice.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.practice.constants.Constants;
import com.practice.dao.StudentDao;
import com.practice.entity.StudentDetail;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
@Repository
public class StudentDaoImpl extends AbstractDao implements StudentDao {

	private String studentQueryBuilder(StudentDetailVO studentDetailVO) {
		final StringBuilder query = new StringBuilder("Select st from StudentDetail st where st.status='ACTIVE' ");

		if (!StringUtils.isEmpty(studentDetailVO.getStudentId())) {
			query.append(Constants.AND).append("st.studentId=:STUDENT_ID");
		}
		if (!StringUtils.isEmpty(studentDetailVO.getStudentName())) {
			query.append(Constants.AND).append("LOWER(st.studentName) ").append("LIKE(LOWER(:STUDENT_NAME))");
		}
		if (!StringUtils.isEmpty(studentDetailVO.getEmailId())) {
			query.append(Constants.AND).append("LOWER(st.emailId) ").append("LIKE(LOWER(:EMAIL))");
		}
		if (!StringUtils.isEmpty(studentDetailVO.getAddress())) {
			query.append(Constants.AND).append("LOWER(st.address) ").append("LIKE(LOWER(:ADDRESS))");
		}

		return query.toString();
	}

	private void setParaMeter(Query query, StudentDetailVO studentDetailVO) {
		if (!StringUtils.isEmpty(studentDetailVO.getStudentId())) {
			query.setParameter("STUDENT_ID", studentDetailVO.getStudentId());
		}
		if (!StringUtils.isEmpty(studentDetailVO.getStudentName())) {
			query.setParameter("STUDENT_NAME", "%" + studentDetailVO.getStudentName() + "%");
		}
		if (!StringUtils.isEmpty(studentDetailVO.getEmailId())) {
			query.setParameter("EMAIL", "%" + studentDetailVO.getEmailId() + "%");
		}
		if (!StringUtils.isEmpty(studentDetailVO.getAddress())) {
			query.setParameter("ADDRESS", "%" + studentDetailVO.getAddress() + "%");
		}
	}

	@Override
	public List<StudentDetail> inquireStudents(StudentDetailVO studentDetailVO) {

		final TypedQuery<StudentDetail> query = entityManager.createQuery(studentQueryBuilder(studentDetailVO),
				StudentDetail.class);
		setParaMeter(query, studentDetailVO);
		return query.getResultList();
	}

	@Override
	public void updateStudent(StudentDetail detail) {

		entityManager.merge(detail);

	}

}
