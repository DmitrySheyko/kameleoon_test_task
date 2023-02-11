package com.dmitrySheyko.kameleoon_test_task.mapper;

import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {



    public static Quote toEntity(InputQuoteDto quoteDto, User creator) {
        if (quoteDto == null) {
            return null;
        }
        return Quote.builder()
                .creator(creator)
                .content(quoteDto.getContent())
                .build();
    }

    public static Quote toEntity(UpdateQuoteDto quoteDto, User creator) {
        if (quoteDto == null) {
            return null;
        }
        return Quote.builder()
                .id(quoteDto.getId())
                .creator(creator)
                .content(quoteDto.getContent())
                .build();
    }

    public static OutputQuoteDto toOutputQuoteDto(Quote quote, int score) {
        if (quote == null) {
            return null;
        }
        return OutputQuoteDto.builder()
                .id(quote.getId())
                .creator(UserMapper.toOutputUserDto(quote.getCreator()))
                .content(quote.getContent())
                .updatedOn(quote.getUpdatedOn())
                .score(score)
                .build();
    }



}
