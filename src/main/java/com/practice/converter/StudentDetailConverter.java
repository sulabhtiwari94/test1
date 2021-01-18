package com.practice.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.practice.entity.StudentDetail;
import com.practice.vo.StudentDetailVO;

/**
 * @author sulabhtiwari
 *
 */

@Mapper(componentModel = "spring")
public interface StudentDetailConverter {

	/**
	 *
	 * @param studentDetailVO
	 * @return
	 */
	@Mappings(value = { @Mapping(target = "audit.createdDateTime", source = "createdDateTime", ignore = true),
			@Mapping(target = "audit.updatedDateTime", source = "updatedDateTime", ignore = true) })
	public StudentDetail voToEntity(StudentDetailVO studentDetailVO);

	/**
	 *
	 * @param studentDetail
	 * @return
	 */
	@Mappings(value = { @Mapping(source = "audit.createdDateTime", target = "createdDateTime"),
			@Mapping(source = "audit.updatedDateTime", target = "updatedDateTime") })
	public StudentDetailVO entityToVO(StudentDetail studentDetail);

}
