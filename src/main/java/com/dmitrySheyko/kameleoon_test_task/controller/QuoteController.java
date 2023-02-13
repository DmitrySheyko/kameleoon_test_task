package com.dmitrySheyko.kameleoon_test_task.controller;

import com.dmitrySheyko.kameleoon_test_task.model.DateScore;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for entity {@link Quote}
 *
 * @author Dmitry Sheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService service;

    @PostMapping
    public OutputQuoteDto add(@Valid @RequestBody InputQuoteDto quoteDto,
                              @RequestParam(name = "id") Long creatorId) {
        return service.add(quoteDto, creatorId);
    }

    @PatchMapping
    public OutputQuoteDto update(@Valid @RequestBody UpdateQuoteDto quoteDto,
                                 @RequestParam(name = "id") Long creatorId) {
        return service.update(quoteDto, creatorId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(name = "id") Long quoteId,
                         @RequestParam(name = "id") Long creatorId) {
        return service.delete(quoteId, creatorId);
    }

    @PatchMapping("/{id}/upvote")
    public OutputQuoteDto upVote(@PathVariable(name = "id") Long quoteId,
                                 @RequestParam(name = "id") Long userId) {
        return service.upVote(quoteId, userId);
    }

    @PatchMapping("/{id}/downvote")
    public OutputQuoteDto downVote(@PathVariable(name = "id") Long quoteId,
                                   @RequestParam(name = "id") Long userId) {
        return service.downVote(quoteId, userId);
    }

    @GetMapping("/{id}")
    public OutputQuoteDto getById(@PathVariable(name = "id") Long quoteId) {
        return service.getById(quoteId);
    }

    @GetMapping
    public List<OutputQuoteDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/top")
    public List<OutputQuoteDto> getTopQuotes() {
        return service.getTopQuotes();
    }

    @GetMapping("/flop")
    public List<OutputQuoteDto> getFlopQuotes() {
        return service.getFlopQuotes();
    }

    @GetMapping("/graph/{id}")
    public List<DateScore> getEvolutionGraph(@PathVariable(name = "id") Long quoteId) {
        return service.getEvolutionGraph(quoteId);
    }

}
