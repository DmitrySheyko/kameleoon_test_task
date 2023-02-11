package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.exception.ValidationException;
import com.dmitrySheyko.kameleoon_test_task.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
        (properties = "spring.sql.init.data-locations=data-test.sql",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class QuoteServiceImplTest {

    private final QuoteServiceImpl quoteService;

    @Test
    void add() {
        InputQuoteDto inputQuoteDto1 = InputQuoteDto.builder()
                .content("TestContent_4")
                .build();
        Long creatorId = 1L;
        OutputQuoteDto outputQuoteDto = quoteService.add(inputQuoteDto1, creatorId);
        Assertions.assertEquals(4, outputQuoteDto.getId());
        Assertions.assertEquals(creatorId, outputQuoteDto.getCreator().getId());
        Assertions.assertEquals("TestContent_4", outputQuoteDto.getContent());
        Assertions.assertEquals(0, outputQuoteDto.getScore());
    }

    @Test
    void update() {
        Long quoteId = 2L;
        Long creatorId = 2L;
        UpdateQuoteDto updateQuoteDto = UpdateQuoteDto.builder()
                .id(quoteId)
                .content("Updated_content")
                .build();
        OutputQuoteDto outputQuoteDto = quoteService.update(updateQuoteDto, creatorId);
        Assertions.assertEquals(quoteId, outputQuoteDto.getId());
        Assertions.assertEquals(creatorId, outputQuoteDto.getCreator().getId());
        Assertions.assertEquals("Updated_content", outputQuoteDto.getContent());
        Assertions.assertEquals(1, outputQuoteDto.getScore());
    }

    @Test
    void delete() {
//        InputQuoteDto inputQuoteDto1 = InputQuoteDto.builder()
//                .content("TestContent_4")
//                .build();
//        Long creatorId = 1L;
//        OutputQuoteDto outputQuoteDto = quoteService.add(inputQuoteDto1, creatorId);
//        Assertions.assertEquals(4, outputQuoteDto.getId());
//
//        quoteService.delete(outputQuoteDto.getId());
    }

    @Test
    void upVote(){
        Long quoteId = 1L;
        Long voterId= 2L;
        int expectedScore = 1;
        OutputQuoteDto outputQuoteDto = quoteService.upVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 3L;
        voterId= 1L;
        expectedScore = 0;
        outputQuoteDto = quoteService.upVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        Long quoteId1 = 2L;
        Long voterId1= 1L;
        Assertions.assertThrows(ValidationException.class, () -> quoteService.upVote(quoteId1, voterId1));
    }

    @Test
    void downVote(){
        Long quoteId = 1L;
        Long voterId= 2L;
        int expectedScore = -1;
        OutputQuoteDto outputQuoteDto = quoteService.downVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        quoteId = 2L;
        voterId= 1L;
        expectedScore = 0;
        outputQuoteDto = quoteService.downVote(quoteId, voterId);
        Assertions.assertEquals(expectedScore, outputQuoteDto.getScore());

        Long quoteId1 = 3L;
        Long voterId1= 1L;
        Assertions.assertThrows(ValidationException.class, () -> quoteService.downVote(quoteId1, voterId1));
    }

}