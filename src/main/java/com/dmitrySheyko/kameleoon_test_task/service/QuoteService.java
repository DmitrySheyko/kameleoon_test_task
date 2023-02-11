package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface QuoteService {

    OutputQuoteDto add(InputQuoteDto quoteDto, Long creatorId);

    OutputQuoteDto update(UpdateQuoteDto quoteDto, Long creatorId);

    String delete(Long quoteId, Long creatorId);

    OutputQuoteDto upVote(Long quoteId, Long userId);

    OutputQuoteDto downVote(Long quoteId, Long userId);

    OutputQuoteDto getById(Long quoteId);

    List<OutputQuoteDto> getTopQuotes();

    List<OutputQuoteDto> getFlopQuotes();

    Map<LocalDateTime, Integer> getEvolutionGraph(Long quoteId);

}