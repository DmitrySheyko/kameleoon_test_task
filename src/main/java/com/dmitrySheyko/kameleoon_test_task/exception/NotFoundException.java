package com.dmitrySheyko.kameleoon_test_task.exception;

/**
 * Class of custom {@link NotFoundException}
 *
 * @author Dmitry Sheyko
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
