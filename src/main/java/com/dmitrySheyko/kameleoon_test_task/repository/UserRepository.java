package com.dmitrySheyko.kameleoon_test_task.repository;

import com.dmitrySheyko.kameleoon_test_task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of JpaRepository for entity {@link User}.
 *
 * @author Dmitry Sheyko
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
