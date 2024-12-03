package com.spring.temp.domain.service.impl;

import com.spring.temp.domain.dto.ExampleDto;
import com.spring.temp.domain.mapper.ExampleMapper;
import com.spring.temp.domain.model.UserActions;
import com.spring.temp.domain.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExampleServiceImpl implements ExampleService {

    private final ExampleMapper exampleMapper;
    @Override
    public void getAll() {
        UserActions userActions = new UserActions();
        // Mapper usage
        ExampleDto exampleDto = exampleMapper.userActionToExampleDto(userActions);
        // Repository actions etc.
    }
}
