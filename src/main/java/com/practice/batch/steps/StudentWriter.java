/**
 *
 */
package com.practice.batch.steps;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.practice.vo.ResponseVO;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class StudentWriter implements ItemWriter<ResponseVO<StudentDetailVO>> {

	@Override
	public void write(List<? extends ResponseVO<StudentDetailVO>> items) throws Exception {
		// items.stream().forEach(t ->
		// System.out.println(t.getResponseVO().getStudentName()));

	}

}
