package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputUserDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputUserDto;

/**
 * Interface of service class for entity {@link User}.
 *
 * @author Dmitry Sheyko
 */
public interface UserService {

    OutputUserDto add(InputUserDto user);

}
