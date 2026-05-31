package com.airtribe.meditrack.exception;

/**
 * Exception thrown when a date value is invalid or violates validation rules.
 * This includes future birth dates, invalid date formats, etc.
 */
public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
