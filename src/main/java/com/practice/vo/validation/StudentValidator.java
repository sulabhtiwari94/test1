/**
 *
 */
package com.practice.vo.validation;

import java.text.MessageFormat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.constants.Constants;
import com.practice.constants.enums.Action;
import com.practice.constants.enums.MessageCodes;
import com.practice.entity.StudentDetail;
import com.practice.repository.StudentDetailsRepository;
import com.practice.util.Messages;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class StudentValidator {

	@Autowired
	private StudentDetailsRepository studentDetailsRepository;

	/**
	 *
	 * @param item
	 * @return
	 */
	public String performTechnicalValidation(StudentDetailVO item) {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();
		final Set<ConstraintViolation<StudentDetailVO>> violations = validator.validate(item,
				StudentMandatoryValidationGroup.class);
		final StringBuilder builder = new StringBuilder(Constants.EMPTY_STRING);
		for (final ConstraintViolation<StudentDetailVO> violation : violations) {
			builder.append(violation.getMessage());
		}

		return builder.toString();
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	public String performFunctionalValidation(final StudentDetailVO item, final Action action) {
		final StringBuilder builder = new StringBuilder(Constants.EMPTY_STRING);
		final StudentDetail studentDetail = studentDetailsRepository.getStudent(item.getStudentId());
		if (!Action.INSERT.equals(action) && null == studentDetail) {
			builder.append(
					MessageFormat.format(Messages.getMesssge(MessageCodes.NO_STUDENT_PRESENT), item.getStudentId()));

		} else if (!Action.INSERT.equals(action) && studentDetail.getVersion() != item.getVersion()) {
			builder.append(MessageFormat.format(Messages.getMesssge(MessageCodes.STUDENT_VERSION_MISMATCH),
					item.getStudentId()));

		} else if ((Action.INSERT.equals(action) || Action.UPDATE.equals(action)) && null != studentDetailsRepository
				.getDuplicateStudent(Action.INSERT.equals(action) ? 0l : item.getStudentId(), item.getMobileNumber(),
						item.getEmailId())) {
			builder.append(MessageFormat.format(Messages.getMesssge(MessageCodes.DUPLICATE_STUDENTS),
					item.getMobileNumber(), item.getEmailId()));
		}

		return builder.toString();
	}

}
