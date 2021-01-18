/**
 *
 */
package com.practice.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.practice.constants.Constants;
import com.practice.constants.enums.Action;
import com.practice.constants.enums.MessageCodes;
import com.practice.converter.StudentDetailConverter;
import com.practice.dao.StudentDao;
import com.practice.entity.StudentDetail;
import com.practice.repository.StudentDetailsRepository;
import com.practice.service.StudentDetailsService;
import com.practice.util.Messages;
import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;
import com.practice.vo.validation.StudentValidator;

/**
 * @author sulabhtiwari
 *
 */
@Service
public class StudentDetailsServiceImpl implements StudentDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsServiceImpl.class);
	@Autowired
	private StudentDetailsRepository studentDetailsRepository;
	@Autowired
	private StudentDetailConverter converter;
	@Autowired
	private StudentValidator studentValidator;
	@Autowired
	private StudentDao studentDao;

	@Transactional
	@Override
	public ResponseVO<StudentDetailVO> insertStudent(final StudentDetailVO studentDetailVO) {
		final ResponseVO<StudentDetailVO> responseVO = new ResponseVO<>();
		final String msg = studentValidator.performTechnicalValidation(studentDetailVO);
		if (!StringUtils.isEmpty(msg)) {
			LOGGER.error(msg);
			responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_ACTION_FAILED),
					studentDetailVO.getStudentId(), Action.INSERT.getActionPerformed(), msg));
		} else {
			final String message = studentValidator.performFunctionalValidation(studentDetailVO, Action.INSERT);
			if (StringUtils.isEmpty(message)) {
				final StudentDetail item = converter.voToEntity(studentDetailVO);
				item.setStatus(Constants.ACTIVE);
				final StudentDetail studentDetail = studentDetailsRepository.save(item);
				responseVO.setResponseVO(converter.entityToVO(studentDetail));
				responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_ACTION),
						studentDetail.getStudentName(), studentDetail.getStudentId(),
						Action.INSERT.getActionPerformed()));
				responseVO.setSuccessCount(1);
			} else {
				LOGGER.error(message);
				responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_ACTION_FAILED),
						studentDetailVO.getStudentId(), Action.INSERT.getActionPerformed(), message));
				responseVO.setSuccessCount(0);
			}

		}

		return responseVO;
	}

	@Override
	public ResponseVO<StudentDetailVO> inquireStudents(final StudentDetailVO studentDetailVO) {
		final ResponseVO<StudentDetailVO> responseVO = new ResponseVO<>();
		final List<StudentDetail> studentDetails = studentDao.inquireStudents(studentDetailVO);
		responseVO.setVoList(studentDetails.stream().map(e -> converter.entityToVO(e)).collect(Collectors.toList()));
		return responseVO;
	}

	@Transactional
	@Override
	public ResponseVO<StudentDetailVO> updateStudents(List<StudentDetailVO> students) {
		final ResponseVO<StudentDetailVO> responseVO = new ResponseVO<>();
		final StringBuilder errorMessages = new StringBuilder();
		int successCount = 0;
		for (final StudentDetailVO studentDetailVO : students) {
			final String msg = studentValidator.performTechnicalValidation(studentDetailVO);
			if (StringUtils.isEmpty(msg)) {
				final String funcString = studentValidator.performFunctionalValidation(studentDetailVO, Action.UPDATE);
				if (StringUtils.isEmpty(funcString)) {

					final StudentDetail detail = studentDetailsRepository.getStudent(studentDetailVO.getStudentId());
					detail.setStudentName(studentDetailVO.getStudentName());
					detail.setAddress(studentDetailVO.getAddress());
					detail.setMobileNumber(studentDetailVO.getMobileNumber());
					detail.setEmailId(studentDetailVO.getEmailId());
					successCount++;
				} else {
					errorMessages.append(funcString);
				}

			} else {
				errorMessages.append(msg);
			}
		}
		return buildResponse(students, responseVO, errorMessages, successCount, Action.UPDATE.getActionPerformed());
	}

	/**
	 * @param students
	 * @param responseVO
	 * @param errorMessages
	 * @param successCount
	 * @return
	 */
	private ResponseVO<StudentDetailVO> buildResponse(List<StudentDetailVO> students,
			final ResponseVO<StudentDetailVO> responseVO, final StringBuilder errorMessages, int successCount,
			final String action) {
		if (students.size() == 1) {
			final StudentDetailVO studentDetail = students.get(0);

			if (errorMessages.length() == 0) {
				responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_ACTION),
						studentDetail.getStudentName(), studentDetail.getStudentId(), action));
			} else {
				responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_ACTION_FAILED),
						studentDetail.getStudentId(), action, errorMessages.toString()));
			}

		} else {
			responseVO.setMessage(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_MULTI_ATION),
					successCount, students.size(), action));
		}
		responseVO.setSuccessCount(successCount);
		responseVO.setVoList(students);

		return responseVO;
	}

	@Transactional
	@Override
	public ResponseVO<StudentDetailVO> deleteStudents(List<StudentDetailVO> students) {

		final ResponseVO<StudentDetailVO> responseVO = new ResponseVO<>();
		final StringBuilder errorMessages = new StringBuilder();
		int successCount = 0;
		for (final StudentDetailVO studentDetailVO : students) {
			final String msg = studentValidator.performTechnicalValidation(studentDetailVO);
			if (StringUtils.isEmpty(msg)) {
				final String funcString = studentValidator.performFunctionalValidation(studentDetailVO, Action.DELETE);
				if (StringUtils.isEmpty(funcString)) {

					final StudentDetail detail = studentDetailsRepository.getStudent(studentDetailVO.getStudentId());
					detail.setStatus(Constants.INACTIVE);
					successCount++;
				} else {
					errorMessages.append(funcString);
				}

			} else {
				errorMessages.append(msg);
			}
		}
		return buildResponse(students, responseVO, errorMessages, successCount, Action.DELETE.getActionPerformed());

	}

}
