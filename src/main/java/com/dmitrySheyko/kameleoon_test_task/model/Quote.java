package com.dmitrySheyko.kameleoon_test_task.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class of entity {@link Quote}.
 *
 * @author Dmitry Sheyko
 */
@Table(name = "quotes")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "content")
    private String content;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Vote> votes;

}
