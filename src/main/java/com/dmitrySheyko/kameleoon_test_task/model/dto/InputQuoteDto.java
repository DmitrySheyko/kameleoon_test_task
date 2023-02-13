package com.dmitrySheyko.kameleoon_test_task.model.dto;

import com.dmitrySheyko.kameleoon_test_task.controller.QuoteController;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Class of InputQuoteDto for entity {@link Quote}.
 * Used for sending data for creation new Quote requests
 * in class {@link QuoteController}
 *
 * @author Dmitry Sheyko
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputQuoteDto {

    @NotBlank (message = "Content should be not blank")
    private String content;

}