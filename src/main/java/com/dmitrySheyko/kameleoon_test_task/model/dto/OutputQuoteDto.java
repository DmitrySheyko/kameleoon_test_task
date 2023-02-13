package com.dmitrySheyko.kameleoon_test_task.model.dto;

import com.dmitrySheyko.kameleoon_test_task.controller.QuoteController;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of InputQuoteDto for entity {@link Quote}.
 * Used for sending data about created or changes Quotes
 * in {@link QuoteController}
 *
 * @author Dmitry Sheyko
 */
@Data
@Builder
public class OutputQuoteDto {

    private Long id;
    private OutputUserDto creator;
    private String content;
    private int score;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedOn;

}