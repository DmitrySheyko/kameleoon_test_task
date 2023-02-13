package com.dmitrySheyko.kameleoon_test_task.exception;

/**
 * Class of custom {@link ValidationException}
 *
 * @author Dmitry Sheyko
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

}
