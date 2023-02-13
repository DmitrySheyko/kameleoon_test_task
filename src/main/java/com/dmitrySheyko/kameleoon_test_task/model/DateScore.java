package com.dmitrySheyko.kameleoon_test_task.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DateScore {
    private LocalDateTime dateTime;
    private int score;
}
