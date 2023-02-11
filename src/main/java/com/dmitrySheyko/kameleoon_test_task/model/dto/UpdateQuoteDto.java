package com.dmitrySheyko.kameleoon_test_task.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateQuoteDto {

    @NotNull (message = "Id should be not null")
    private Long id;
    private String content;

}
