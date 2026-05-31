package com.airtribe.meditrack.exception;

/**
 * Exception thrown when a date is outside an acceptable range.
 * For example: birth date too far in the past, appointment date in the past, etc.
 */
public class DateOutOfRangeException extends RuntimeException {
    public DateOutOfRangeException(String message) {
        super(message);
    }

    public DateOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
