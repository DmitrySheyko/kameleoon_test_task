package com.dmitrySheyko.kameleoon_test_task.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputUserDto {

    @NotBlank(message = "Name should be not blank")
    private String name;

    @NotBlank(message = "Password should be not blank")
    private String password;

    @Email(message = "Incorrect format of email")
    private String email;

}
