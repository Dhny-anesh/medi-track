package com.airtribe.meditrack.util;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9\\-\\s()+]{7,20}$");

    private Validator() {
        throw new UnsupportedOperationException("Validator cannot be instantiated");
    }

    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public static void validateEmail(String email) {
        requireNonEmpty(email, "Email");
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        requireNonEmpty(phoneNumber, "Phone number");
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid phone number format: " + phoneNumber);
        }
    }

    public static void validateFutureDateTime(String dateTime) {
        requireNonEmpty(dateTime, "Date and time");
        if (!DateUtil.isFuture(DateUtil.parse(dateTime))) {
            throw new IllegalArgumentException("Date and time must be in the future: " + dateTime);
        }
    }

    public static void validateAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void requireNonNull(Object object, String fieldName) {
        Objects.requireNonNull(object, fieldName + " cannot be null");
    }
}
