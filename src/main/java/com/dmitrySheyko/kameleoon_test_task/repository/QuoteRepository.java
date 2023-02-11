package com.dmitrySheyko.kameleoon_test_task.repository;

import com.dmitrySheyko.kameleoon_test_task.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}