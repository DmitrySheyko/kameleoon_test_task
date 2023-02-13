package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputUserDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputUserDto;
import com.dmitrySheyko.kameleoon_test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations=data-test.sql",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final UserService service;
    private final UserRepository repository;

    @Test
    void shouldAddNewUser() {
        List<User> usersList = repository.findAll();
        Assertions.assertEquals(5, usersList.size());

        InputUserDto inputUserDto = InputUserDto.builder()
                .name("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .build();
        OutputUserDto outputUserDto = service.add(inputUserDto);
        Assertions.assertEquals(6, outputUserDto.getId());
        Assertions.assertEquals("TestName_6", outputUserDto.getName());
        Assertions.assertEquals("TestPassword_6", outputUserDto.getPassword());
        Assertions.assertEquals("Test_6@email.ru", outputUserDto.getEmail());

        usersList = repository.findAll();
        Assertions.assertEquals(6, usersList.size());
    }

    @Test
    void shouldThrowExceptionInCaseOfNewUserWithSameEmail() {
        InputUserDto dtoWithSameEmail = InputUserDto.builder()
                .name("TestName")
                .password("TestPassword")
                .email("Test_1@email.ru")
                .build();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.add(dtoWithSameEmail));
    }

}