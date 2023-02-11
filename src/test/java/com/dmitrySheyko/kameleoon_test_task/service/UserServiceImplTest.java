package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.model.dto.InputUserDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputUserDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations=data-test.sql",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final UserServiceImpl userService;

    @Test
    void add() {
        InputUserDto inputUserDto1 = InputUserDto.builder()
                .name("TestName_3")
                .password("TestPassword_3")
                .email("Test_3@mail.ru")
                .build();
        OutputUserDto outputUserDto = userService.add(inputUserDto1);
        Assertions.assertEquals(3, outputUserDto.getId());
        Assertions.assertEquals("TestName_3", outputUserDto.getName());
        Assertions.assertEquals("TestPassword_3", outputUserDto.getPassword());
        Assertions.assertEquals("Test_3@mail.ru", outputUserDto.getEmail());

        InputUserDto dtoWithSameEmail = InputUserDto.builder()
                .name("TestName_4")
                .password("TestPassword_4")
                .email("Test_3@mail.ru")
                .build();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.add(dtoWithSameEmail));
    }

}