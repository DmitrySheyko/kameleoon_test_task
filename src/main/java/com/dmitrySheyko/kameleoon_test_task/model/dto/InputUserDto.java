package com.dmitrySheyko.kameleoon_test_task.model.dto;

import com.dmitrySheyko.kameleoon_test_task.controller.UserController;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Class of InputUserDto for entity {@link User}.
 * Used for getting data for creation new User requests
 * in {@link UserController}
 *
 * @author Dmitry Sheyko
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputUserDto {

    @NotBlank(message = "Name should be not blank")
    private String name;

    @NotBlank(message = "Password should be not blank")
    private String password;

    @Email(message = "Incorrect format of email")
    private String email;

}