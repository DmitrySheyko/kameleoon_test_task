package com.dmitrySheyko.kameleoon_test_task.controller;

import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/{id}/upvote")
    public OutputQuoteDto upVote(@PathVariable(name = "id") Long quoteId,
                                 @RequestParam(name = "id") Long userId) {
        return service.upVote(quoteId, userId);
    }

    @PostMapping("/{id}/downvote")
    public OutputQuoteDto downVote(@PathVariable(name = "id") Long quoteId,
                                   @RequestParam(name = "id") Long userId) {
        return service.downVote(quoteId, userId);
    }

    @GetMapping("/{id}")
    public OutputQuoteDto getById(@PathVariable(name = "id") Long quoteId) {
        return service.getById(quoteId);
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
public Map<LocalDateTime, Integer> getEvolutionGraph(@PathVariable(name = "id") Long quoteId){
    return service.getEvolutionGraph(quoteId);
    }
}
