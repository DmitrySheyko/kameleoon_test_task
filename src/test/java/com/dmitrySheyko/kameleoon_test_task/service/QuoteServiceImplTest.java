package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.exception.ValidationException;
import com.dmitrySheyko.kameleoon_test_task.model.DateScore;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.dto.*;
import com.dmitrySheyko.kameleoon_test_task.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@SpringBootTest
        (properties = "spring.sql.init.data-locations=data-test.sql",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class QuoteServiceImplTest {

    private final QuoteService service;
    private final QuoteRepository repository;
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ISO_DATE_TIME;

    @Test
    void shouldAddNewQuote() {
        List<Quote> quotesList = repository.findAll();
        Assertions.assertEquals(3, quotesList.size());

        InputQuoteDto inputQuoteDto = InputQuoteDto.builder()
                .content("TestContent_4")
                .build();
        Long creatorId = 1L;
        OutputQuoteDto outputQuoteDto = service.add(inputQuoteDto, creatorId);
        Assertions.assertEquals(creatorId, outputQuoteDto.getCreator().getId());
        Assertions.assertEquals("TestContent_4", outputQuoteDto.getContent());
        Assertions.assertEquals(0, outputQuoteDto.getScore());

        quotesList = repository.findAll();
        Assertions.assertEquals(4, quotesList.size());
    }

    @Test
    void shouldUpdateQuote() {
        Long quoteForUpdateId = 2L;
        String contentForUpdate = "Updated_content";
        Long creatorId = 2L;
        UpdateQuoteDto updateQuoteDto = UpdateQuoteDto.builder()
                .id(quoteForUpdateId)
                .content(contentForUpdate)
                .build();
        OutputQuoteDto outputQuoteDto = service.update(updateQuoteDto, creatorId);
        Assertions.assertEquals(quoteForUpdateId, outputQuoteDto.getId());
        Assertions.assertEquals(creatorId, outputQuoteDto.getCreator().getId());
        Assertions.assertEquals(contentForUpdate, outputQuoteDto.getContent());
        Assertions.assertEquals(1, outputQuoteDto.getScore());
    }

    @Test
    void shouldDeleteQuote() {
        List<Quote> quotesList = repository.findAll();
        Assertions.assertEquals(3, quotesList.size());

        InputQuoteDto inputQuoteDto = InputQuoteDto.builder()
                .content("TestContent_4")
                .build();
        Long creatorId = 1L;
        OutputQuoteDto outputQuoteDto = service.add(inputQuoteDto, creatorId);

        quotesList = repository.findAll();
        Assertions.assertEquals(4, quotesList.size());

        service.delete(outputQuoteDto.getId(), creatorId);

        quotesList = repository.findAll();
        Assertions.assertEquals(3, quotesList.size());
    }

    @Test
    void upVote() {
        long quoteId = 1L;
        long voterId = 5L;
        int expectedScore = 4;
        OutputQuoteDto outputQuoteDto = service.upVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 2L;
        voterId = 5L;
        expectedScore = 2;
        outputQuoteDto = service.upVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 3L;
        voterId = 5L;
        expectedScore = -2;
        outputQuoteDto = service.upVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        Long quoteId1 = 2L;
        Long voterId1 = 1L;
        Assertions.assertThrows(ValidationException.class, () -> service.upVote(quoteId1, voterId1));
    }

    @Test
    void downVote() {
        long quoteId = 1L;
        long voterId = 5L;
        int expectedScore = 2;
        OutputQuoteDto outputQuoteDto = service.downVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 2L;
        voterId = 5L;
        expectedScore = 0;
        outputQuoteDto = service.downVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 3L;
        voterId = 5L;
        expectedScore = -4;
        outputQuoteDto = service.downVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        Long quoteId1 = 3L;
        Long voterId1 = 1L;
        Assertions.assertThrows(ValidationException.class, () -> service.downVote(quoteId1, voterId1));
    }

    @Test
    void shouldGetTopQuotes() {
        long bestQuoteId = 1L;
        long secondQuoteId = 2L;
        long thirdQuoteId = 3L;
        List<OutputQuoteDto> dtoList = service.getTopQuotes();
        Assertions.assertEquals(bestQuoteId, dtoList.get(0).getId());
        Assertions.assertEquals(secondQuoteId, dtoList.get(1).getId());
        Assertions.assertEquals(thirdQuoteId, dtoList.get(2).getId());
    }

    @Test
    void shouldGetWorseQuotes() {
        long worseQuoteId = 3L;
        long secondQuoteId = 2L;
        long thirdQuoteId = 1L;
        List<OutputQuoteDto> dtoList = service.getFlopQuotes();
        Assertions.assertEquals(worseQuoteId, dtoList.get(0).getId());
        Assertions.assertEquals(secondQuoteId, dtoList.get(1).getId());
        Assertions.assertEquals(thirdQuoteId, dtoList.get(2).getId());
    }

    @Test
    void shouldGetEvolutionGraph() {
        long quoteId = 1L;
        List<DateScore> dataScoreList = service.getEvolutionGraph(quoteId);
        Assertions.assertEquals(LocalDateTime.parse("2018-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(0).getDateTime());
        Assertions.assertEquals(1, dataScoreList.get(0).getScore());

        Assertions.assertEquals(LocalDateTime.parse("2019-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(1).getDateTime());
        Assertions.assertEquals(2, dataScoreList.get(1).getScore());

        Assertions.assertEquals(LocalDateTime.parse("2020-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(2).getDateTime());
        Assertions.assertEquals(3, dataScoreList.get(2).getScore());

        quoteId = 2L;
        dataScoreList = service.getEvolutionGraph(quoteId);
        Assertions.assertEquals(LocalDateTime.parse("2020-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(0).getDateTime());
        Assertions.assertEquals(1, dataScoreList.get(0).getScore());

        Assertions.assertEquals(LocalDateTime.parse("2021-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(1).getDateTime());
        Assertions.assertEquals(2, dataScoreList.get(1).getScore());

        Assertions.assertEquals(LocalDateTime.parse("2022-01-01T10:10:10", DATE_TIME_PATTERN),
                dataScoreList.get(2).getDateTime());
        Assertions.assertEquals(1, dataScoreList.get(2).getScore());
    }

}