package com.spring.temp.domain.mapper;

import com.spring.temp.domain.dto.ExampleDto;
import com.spring.temp.domain.model.UserActions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleMapper {


    // Mapstruct example usage
    ExampleDto userActionToExampleDto(UserActions userActions);

}
