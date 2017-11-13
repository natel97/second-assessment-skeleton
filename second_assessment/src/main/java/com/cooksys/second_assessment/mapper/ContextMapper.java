package com.cooksys.second_assessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second_assessment.Dto.ContextDto;
import com.cooksys.second_assessment.entity.Context;

@Mapper(componentModel = "spring")
public interface ContextMapper {
	ContextDto toContextPojo(Context contextDao);
	Context toContextDao(ContextDto contextPojo);
}
