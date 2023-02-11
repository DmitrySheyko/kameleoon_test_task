package com.dmitrySheyko.kameleoon_test_task.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;
    private String reason;
    private String status;

    public ErrorResponse(String message, String reason, String status) {
        this.message = message;
        this.reason = reason;
        this.status = status;
    }

}
