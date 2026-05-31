package com.airtribe.meditrack.exception;

/**
 * Exception thrown when a time value is invalid or violates validation rules.
 * This includes invalid time formats, past appointment times, etc.
 */
public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
