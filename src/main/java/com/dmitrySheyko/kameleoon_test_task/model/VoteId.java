package com.dmitrySheyko.kameleoon_test_task.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class VoteId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private User user;

}