package com.dmitrySheyko.kameleoon_test_task.repository;

import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import com.dmitrySheyko.kameleoon_test_task.model.Vote;
import com.dmitrySheyko.kameleoon_test_task.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {

    @Query(value = "SELECT " +
            "(SELECT COUNT(q.UPDATED_ON) " +
            "FROM quote_vote q " +
            "WHERE q.quote_id = ?1 " +
            "AND q.is_positive = true) - " +
            "(SELECT COUNT(q.UPDATED_ON)" +
            "FROM quote_vote q " +
            "WHERE q.quote_id = ?1 " +
            "AND q.is_positive = false);", nativeQuery = true)
    int calculateScore(Long quoteId);

    @Query(value = "SELECT q.id " +
            "FROM (SELECT q1.quote_id id, " +
            "((SELECT COUNT(q2.UPDATED_ON) " +
            "FROM quote_vote q2\n" +
            "WHERE q2.quote_id = q1.quote_id " +
            "AND q2.is_positive = true) - " +
            "(SELECT COUNT(q2.UPDATED_ON) " +
            "FROM quote_vote q2 " +
            "WHERE q2.quote_id = q1.quote_id " +
            "AND q2.is_positive = false)) score " +
            "FROM quote_vote q1) as q " +
            "GROUP BY q.id " +
            "ORDER BY score DESC " +
            "LIMIT 10;", nativeQuery = true)
    List<Long> getTopQuotesId();

    @Query(value = "SELECT q.id " +
            "FROM (SELECT q1.quote_id id, " +
            "((SELECT COUNT(q2.UPDATED_ON) " +
            "FROM quote_vote q2\n" +
            "WHERE q2.quote_id = q1.quote_id " +
            "AND q2.is_positive = true) - " +
            "(SELECT COUNT(q2.UPDATED_ON) " +
            "FROM quote_vote q2 " +
            "WHERE q2.quote_id = q1.quote_id " +
            "AND q2.is_positive = false)) score " +
            "FROM quote_vote q1) as q " +
            "GROUP BY q.id " +
            "ORDER BY score " +
            "LIMIT 10;", nativeQuery = true)
    List<Long> getFlopQuotesId();

    @Query("SELECT v " +
            "FROM Vote v " +
            "WHERE v.id.quote = :quote " +
            "ORDER BY v.updatedOn ")
    List<Vote>findAllByQuoteId(Quote quote);

//    @Query(value = "SELECT q. " +
//            "FROM quote_vote q " +
//            "WHERE q.QUOTE_ID = ?1 ", nativeQuery = true)
//    Map<LocalDateTime, Integer> getEvolutionGraph(Long quoteId);

}