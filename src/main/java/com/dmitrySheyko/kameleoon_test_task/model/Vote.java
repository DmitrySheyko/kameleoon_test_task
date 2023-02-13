package com.dmitrySheyko.kameleoon_test_task.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class of entity {@link Vote}.
 *
 * @author Dmitry Sheyko
 */
@Table(name = "quote_vote")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private User voter;

    @Column(name = "is_positive")
    private Boolean isPositive;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

}