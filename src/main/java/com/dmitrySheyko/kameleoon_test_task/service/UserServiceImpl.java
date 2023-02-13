package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.mapper.UserMapper;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputUserDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputUserDto;
import com.dmitrySheyko.kameleoon_test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Class of service for entity {@link User}.
 * Implementation of interface {@link UserService}
 *
 * @author Dmitry Sheyko
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public OutputUserDto add(InputUserDto userDto) {
        User newUser = UserMapper.toEntity(userDto);
        newUser.setCreatedOn(LocalDateTime.now());
        newUser = repository.save(newUser);
        OutputUserDto outputUserDto = UserMapper.toOutputUserDto(newUser);
        log.info("Created user id={}", newUser.getId());
        return outputUserDto;
    }

}