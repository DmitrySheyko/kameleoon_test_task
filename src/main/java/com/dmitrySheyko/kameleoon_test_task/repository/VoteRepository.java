package com.dmitrySheyko.kameleoon_test_task.repository;

import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.User;
import com.dmitrySheyko.kameleoon_test_task.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Interface of JpaRepository for entity {@link Vote}.
 *
 * @author Dmitry Sheyko
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByQuoteAndVoter(Quote quote, User voter);

    @Query(value = """
            SELECT 
            (SELECT COUNT(q.UPDATED_ON) 
            FROM quote_vote q 
            WHERE q.quote_id = ?1 
            AND q.is_positive = true) - 
            (SELECT COUNT(q.UPDATED_ON) 
            FROM quote_vote q 
            WHERE q.quote_id = ?1 
            AND q.is_positive = false); """, nativeQuery = true)
    int calculateScore(Long quoteId);

    @Query("""
            SELECT v 
            FROM Vote v 
            WHERE v.quote = :quote 
            ORDER BY v.updatedOn """)
    List<Vote> findAllByQuote(Quote quote);

}