package com.dmitrySheyko.kameleoon_test_task.model.dto;

import com.dmitrySheyko.kameleoon_test_task.controller.UserController;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of InputQuoteDto for entity {@link User}.
 * Used for sending data about created Users
 * in {@link UserController}
 *
 * @author Dmitry Sheyko
 */
@Data
@Builder
public class OutputUserDto {

    private Long id;
    private String name;
    private String password;
    private String email;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;

}