package com.dmitrySheyko.kameleoon_test_task.mapper;

import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputUserDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(InputUserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .name(userDto.getName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();

    }

    public static OutputUserDto toOutputUserDto(User user){
        if (user == null) {
            return null;
        }
        return OutputUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .createdOn(user.getCreatedOn())
                .build();

    }
}
