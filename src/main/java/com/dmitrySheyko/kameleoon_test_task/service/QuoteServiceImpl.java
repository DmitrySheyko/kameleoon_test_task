package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.exception.NotFoundException;
import com.dmitrySheyko.kameleoon_test_task.exception.ValidationException;
import com.dmitrySheyko.kameleoon_test_task.mapper.QuoteMapper;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.Vote;
import com.dmitrySheyko.kameleoon_test_task.model.VoteId;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.repository.QuoteRepository;
import com.dmitrySheyko.kameleoon_test_task.repository.UserRepository;
import com.dmitrySheyko.kameleoon_test_task.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository repository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private static final int INITIAL_SCORE = 0;

    @Override
    public OutputQuoteDto add(InputQuoteDto quoteDto, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't create. User id=%s not found", creatorId)));
        Quote newQuote = QuoteMapper.toEntity(quoteDto, creator);
        newQuote.setUpdatedOn(LocalDateTime.now());
        newQuote = repository.save(newQuote);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(newQuote, INITIAL_SCORE);
        log.info("Created quote id={}", newQuote.getId());
        return outputQuoteDto;
    }

    @Override
    public OutputQuoteDto update(UpdateQuoteDto quoteDto, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't update. User id=%s not found", creatorId)));
        if (!quoteDto.getId().equals(creatorId)) {
            throw new ValidationException(String.format("Quote didn't update. User id=%s don't have rights", creatorId));
        }
        Quote quoteForUpdate = QuoteMapper.toEntity(quoteDto, creator);
        Quote oldQuote = repository.findById(quoteForUpdate.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't update. Quote id=%s not found", quoteForUpdate.getId())));
        oldQuote.setContent(Optional.ofNullable(quoteForUpdate.getContent()).orElse(oldQuote.getContent()));
        oldQuote.setUpdatedOn(LocalDateTime.now());
        oldQuote = repository.save(oldQuote);
        int currentScore = voteRepository.calculateScore(oldQuote.getId());
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(oldQuote, currentScore);
        log.info("Quote id={} successfully updated", quoteDto.getId());
        return outputQuoteDto;
    }

    public String delete(Long quoteId, Long creatorId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't delete. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't delete. User id=%s not found", creatorId)));
        if (!quote.getCreator().equals(user)) {
            throw new ValidationException(String.format("Quote didn't delete. User id=%s don't have rights", creatorId));
        }
        repository.delete(quote);  // Проверить что возвращает
        log.info("Quote id={} successfully deleted", quoteId);
        return String.format("Quote id=%s successfully deleted", quoteId);
    }

    @Override
    public OutputQuoteDto upVote(Long quoteId, Long userId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't downed. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't downed. User id=%s not found", userId)));
        VoteId voteId = VoteId.builder().quote(quote).user(user).build();
        Vote vote;
        if (voteRepository.existsById(voteId)) {
            vote = voteRepository.findById(voteId)
                    .orElseThrow(() -> new NotFoundException(String.format("Vote, quoteId=%s, UserId=%s not found", quoteId, userId)));
            if (vote.getIsPositive()) {
                throw new ValidationException(String.format("User id=%s already voted up quote id=%s", userId, quoteId));
            } else {
                voteRepository.deleteById(voteId);
            }
        } else {
            vote = Vote.builder().id(voteId).isPositive(true).updatedOn(LocalDateTime.now()).build();
            voteRepository.save(vote);
        }
        quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't delete. Quote id=%s not found", quoteId)));
        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("User id={} successfully down quote id={} ", userId, quoteId);
        return outputQuoteDto;
    }

    @Override
    public OutputQuoteDto downVote(Long quoteId, Long userId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't downed. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't downed. User id=%s not found", userId)));
        VoteId voteId = VoteId.builder().quote(quote).user(user).build();
        Vote vote;
        if (voteRepository.existsById(voteId)) {
            vote = voteRepository.findById(voteId)
                    .orElseThrow(() -> new NotFoundException(String.format("Vote, quoteId=%s, UserId=%s not found", quoteId, userId)));
            if (!vote.getIsPositive()) {
                throw new ValidationException(String.format("User id=%s already voted down quote id=%s", userId, quoteId));
            } else {
                voteRepository.delete(vote);
            }
        } else {
            vote = Vote.builder().id(voteId).isPositive(false).updatedOn(LocalDateTime.now()).build();
            voteRepository.save(vote);
        }
        quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote didn't delete. Quote id=%s not found", quoteId)));
        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("User id={} successfully down quote id={} ", userId, quoteId);
        return outputQuoteDto;
    }

    @Override
    public OutputQuoteDto getById(Long quoteId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote id=%s not found", quoteId)));
        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("Quote id={} successfully received", quoteId);
        return outputQuoteDto;
    }

    @Override
    public List<OutputQuoteDto> getTopQuotes() {
        List<Long> topQuotesIdList = voteRepository.getTopQuotesId();
        List<Quote> topQuotesList = repository.findAllById(topQuotesIdList);
        List<OutputQuoteDto> topQuotesDtoList = topQuotesList.stream()
                .map(quote -> {
                    int currentScore = voteRepository.calculateScore(quote.getId());
                    return QuoteMapper.toOutputQuoteDto(quote, currentScore);
                })
                .toList();
        log.info("List of top quotes successfully received");
        return topQuotesDtoList;
    }

    @Override
    public List<OutputQuoteDto> getFlopQuotes() {
        List<Long> flopQuotesIdList = voteRepository.getFlopQuotesId();
        List<Quote> flopQuotesList = repository.findAllById(flopQuotesIdList);
        List<OutputQuoteDto> flopQuotesDtoList = flopQuotesList.stream()
                .map(quote -> {
                    int currentScore = voteRepository.calculateScore(quote.getId());
                    return QuoteMapper.toOutputQuoteDto(quote, currentScore);
                })
                .toList();
        log.info("List of flop quotes successfully received");
        return flopQuotesDtoList;
    }

    @Override
    public Map<LocalDateTime, Integer> getEvolutionGraph(Long quoteId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote id=%s not found", quoteId)));
        List<Vote> votesList = voteRepository.findAllByQuoteId(quote);
        int score = 0;
        Map<LocalDateTime, Integer> timeScoreMap = new HashMap<>();
        for (Vote vote : votesList) {
            if (vote.getIsPositive()) {
                score++;
            } else {
                score--;
            }
            timeScoreMap.put(vote.getUpdatedOn(), score);
        }
        log.info("Evolution for quote id={} successfully received", quoteId);
        return timeScoreMap;
    }

}