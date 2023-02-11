package com.dmitrySheyko.kameleoon_test_task.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputQuoteDto {

//    @NotNull(message = "Creator should be not null")
//    private Long creator;

    @NotBlank (message = "Content should be not blank")
    private String content;

}