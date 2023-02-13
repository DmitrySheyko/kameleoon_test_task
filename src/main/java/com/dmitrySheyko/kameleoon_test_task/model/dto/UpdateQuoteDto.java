package com.dmitrySheyko.kameleoon_test_task.model.dto;

import com.dmitrySheyko.kameleoon_test_task.controller.QuoteController;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Class of UpdateQuoteDto for entity {@link Quote}.
 * Used for getting data for update Quote requests
 * in {@link QuoteController}
 *
 * @author Dmitry Sheyko
 */
@Data
@Builder
public class UpdateQuoteDto {

    @NotNull(message = "Id should be not null")
    private Long id;
    private String content;

}
