package com.dmitrySheyko.kameleoon_test_task.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "quote_vote") //TODO переместить вверх везде
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @EmbeddedId
    private VoteId id = new VoteId();

    @Column(name = "is_positive")
    private Boolean isPositive;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "total_score")
    private Integer totalScore;

}