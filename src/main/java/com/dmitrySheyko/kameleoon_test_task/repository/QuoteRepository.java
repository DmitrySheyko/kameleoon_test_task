package com.dmitrySheyko.kameleoon_test_task.repository;

import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface of JpaRepository for entity {@link Quote}.
 *
 * @author Dmitry Sheyko
 */
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = """
            SELECT quotes.* 
            FROM quotes right join (SELECT q.id
                FROM (SELECT q1.quote_id id,
                    ((SELECT COUNT(q2.UPDATED_ON)
                    FROM quote_vote q2
                    WHERE q2.quote_id = q1.quote_id
                    AND q2.is_positive = true) -
                    (SELECT COUNT(q2.UPDATED_ON)
                    FROM quote_vote q2
                    WHERE q2.quote_id = q1.quote_id
                    AND q2.is_positive = false)) score
                    FROM quote_vote q1) as q
                    GROUP BY q.id
                    ORDER BY score DESC) reting
            ON quotes.id = reting.id
            LIMIT 10; """, nativeQuery = true)
    List<Quote> getTopQuotes();

    @Query(value = """
            SELECT quotes.* 
            FROM quotes right join (SELECT q.id
                FROM (SELECT q1.quote_id id,
                    ((SELECT COUNT(q2.UPDATED_ON)
                    FROM quote_vote q2
                    WHERE q2.quote_id = q1.quote_id
                    AND q2.is_positive = true) -
                    (SELECT COUNT(q2.UPDATED_ON)
                    FROM quote_vote q2
                    WHERE q2.quote_id = q1.quote_id
                    AND q2.is_positive = false)) score
                    FROM quote_vote q1) as q
                    GROUP BY q.id
                    ORDER BY score ) reting
            ON quotes.id = reting.id
            LIMIT 10; """, nativeQuery = true)
    List<Quote> getFlopQuotes();

}