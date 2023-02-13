package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.model.DateScore;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;

import java.util.List;

/**
 * Interface of service class for entity {@link Quote}.
 *
 * @author Dmitry Sheyko
 */
public interface QuoteService {

    OutputQuoteDto add(InputQuoteDto quoteDto, Long creatorId);

    OutputQuoteDto update(UpdateQuoteDto quoteDto, Long creatorId);

    String delete(Long quoteId, Long creatorId);

    OutputQuoteDto upVote(Long quoteId, Long userId);

    OutputQuoteDto downVote(Long quoteId, Long userId);

    OutputQuoteDto getById(Long quoteId);

    List<OutputQuoteDto> getAll();

    List<OutputQuoteDto> getTopQuotes();

    List<OutputQuoteDto> getFlopQuotes();

    List<DateScore> getEvolutionGraph(Long quoteId);

}