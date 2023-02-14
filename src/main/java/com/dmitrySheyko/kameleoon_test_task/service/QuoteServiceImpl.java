package com.dmitrySheyko.kameleoon_test_task.service;

import com.dmitrySheyko.kameleoon_test_task.exception.NotFoundException;
import com.dmitrySheyko.kameleoon_test_task.exception.ValidationException;
import com.dmitrySheyko.kameleoon_test_task.mapper.QuoteMapper;
import com.dmitrySheyko.kameleoon_test_task.model.DateScore;
import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.Vote;
import com.dmitrySheyko.kameleoon_test_task.model.dto.InputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.OutputQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.model.dto.UpdateQuoteDto;
import com.dmitrySheyko.kameleoon_test_task.repository.QuoteRepository;
import com.dmitrySheyko.kameleoon_test_task.repository.UserRepository;
import com.dmitrySheyko.kameleoon_test_task.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Class of service for entity {@link Quote}.
 * Implementation of interface {@link QuoteService}
 *
 * @author Dmitry Sheyko
 */
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository repository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private static final int INITIAL_SCORE = 0;

    @Override
    public OutputQuoteDto add(InputQuoteDto quoteDto, Long creatorId) {
        User creator = userRepository.findById(creatorId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't create. User id=%s not found", creatorId)));
        Quote newQuote = QuoteMapper.toEntity(quoteDto, creator);
        newQuote.setUpdatedOn(LocalDateTime.now());
        newQuote = repository.save(newQuote);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(newQuote, INITIAL_SCORE);
        log.info("Created quote id={}", newQuote.getId());
        return outputQuoteDto;
    }

    @Override
    public OutputQuoteDto update(UpdateQuoteDto quoteDto, Long creatorId) {
        User creator = userRepository.findById(creatorId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't update. User id=%s not found", creatorId)));
        if (!quoteDto.getId().equals(creatorId)) {
            throw new ValidationException(String.format("Quote didn't update. User id=%s don't have rights", creatorId));
        }
        Quote quoteForUpdate = QuoteMapper.toEntity(quoteDto, creator);
        Quote oldQuote = repository.findById(quoteForUpdate.getId()).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't update. Quote id=%s not found", quoteForUpdate.getId())));
        oldQuote.setContent(Optional.ofNullable(quoteForUpdate.getContent()).orElse(oldQuote.getContent()));
        oldQuote.setUpdatedOn(LocalDateTime.now());
        oldQuote = repository.save(oldQuote);
        int currentScore = voteRepository.calculateScore(oldQuote.getId());
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(oldQuote, currentScore);
        log.info("Quote id={} successfully updated", quoteDto.getId());
        return outputQuoteDto;
    }

    public String delete(Long quoteId, Long creatorId) {
        Quote quote = repository.findById(quoteId).orElseThrow(() -> new NotFoundException(String.
                format("Quote didn't delete. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(creatorId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't delete. User id=%s not found", creatorId)));
        if (!quote.getCreator().equals(user)) {
            throw new ValidationException(String.format("Quote didn't delete. User id=%s don't have rights", creatorId));
        }
        repository.deleteById(quoteId);
        log.info("Quote id={} successfully deleted", quoteId);
        return String.format("Quote id=%s successfully deleted", quoteId);
    }

    /*
     * First click on button (+) will up quote.
     * Repeat click on button (+) will throw exception.
     * Click on button (-) after first click on button (+) will delete firs vote.
     */
    @Override
    public OutputQuoteDto upVote(Long quoteId, Long userId) {
        Quote quote = repository.findById(quoteId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't upvoted. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't upvoted. User id=%s not found", userId)));
        Optional<Vote> optionalVote = voteRepository.findByQuoteAndVoter(quote, user);
        Vote vote;
        if (optionalVote.isPresent()) {
            vote = optionalVote.get();
            if (vote.getIsPositive()) {
                throw new ValidationException(String.format("User id=%s already up quote id=%s", userId, quoteId));
            } else {
                voteRepository.delete(vote);
            }
        } else {
            vote = Vote.builder().quote(quote).voter(user).isPositive(true).updatedOn(LocalDateTime.now()).build();
            voteRepository.save(vote);
        }

        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("User id={} successfully down quote id={} ", userId, quoteId);
        return outputQuoteDto;
    }

    /*
     * First click on button (-) will down quote.
     * Repeat click on button (-) will throw exception.
     * Click on button (+) after first click on button (-) will delete firs vote.
     */
    @Override
    public OutputQuoteDto downVote(Long quoteId, Long userId) {
        Quote quote = repository.findById(quoteId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't downed. Quote id=%s not found", quoteId)));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Quote didn't downed. User id=%s not found", userId)));
        Optional<Vote> optionalVote = voteRepository.findByQuoteAndVoter(quote, user);
        Vote vote;
        if (optionalVote.isPresent()) {
            vote = optionalVote.get();
            if (!vote.getIsPositive()) {
                throw new ValidationException(String.format("User id=%s already down quote id=%s", userId, quoteId));
            } else {
                voteRepository.delete(vote);
            }
        } else {
            vote = Vote.builder().quote(quote).voter(user).isPositive(false).updatedOn(LocalDateTime.now()).build();
            voteRepository.save(vote);
        }

        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("User id={} successfully down quote id={} ", userId, quoteId);
        return outputQuoteDto;
    }

    @Override
    @Transactional(readOnly = true)
    public OutputQuoteDto getById(Long quoteId) {
        Quote quote = repository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(String.format("Quote id=%s not found", quoteId)));
        int currentScore = voteRepository.calculateScore(quoteId);
        OutputQuoteDto outputQuoteDto = QuoteMapper.toOutputQuoteDto(quote, currentScore);
        log.info("Quote id={} successfully received", quoteId);
        return outputQuoteDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutputQuoteDto> getAll() {
        List<Quote> quotesList = repository.findAll();
        List<OutputQuoteDto> quoteDtoList = quotesList.stream()
                .map(quote -> {
                    int currentScore = voteRepository.calculateScore(quote.getId());
                    return QuoteMapper.toOutputQuoteDto(quote, currentScore);
                })
                .toList();
        log.info("List of quotes successfully received");
        return quoteDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutputQuoteDto> getTopQuotes() {
        List<Quote> topQuotesList = repository.getTopQuotes();
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
    @Transactional(readOnly = true)
    public List<OutputQuoteDto> getFlopQuotes() {
        List<Quote> flopQuotesList = repository.getFlopQuotes();
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
    @Transactional(readOnly = true)
    public List<DateScore> getEvolutionGraph(Long quoteId) {
        Quote quote = repository.findById(quoteId).orElseThrow(() -> new NotFoundException(String
                .format("Quote id=%s not found", quoteId)));
        List<Vote> votesList = voteRepository.findAllByQuote(quote);
        List<DateScore> dataScoreList = new ArrayList<>();
        int score = 0;
        for (Vote vote : votesList) {
            if (vote.getIsPositive()) {
                score++;
            } else {
                score--;
            }
            dataScoreList.add(DateScore.builder().dateTime(vote.getUpdatedOn()).score(score).build());
        }
        log.info("Evolution for quote id={} successfully received", quoteId);
        return dataScoreList;
    }

}