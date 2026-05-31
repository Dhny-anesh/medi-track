package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public final class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    private DateUtil() {
        throw new UnsupportedOperationException("DateUtil cannot be instantiated");
    }

    public static LocalDateTime parse(String input) {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Invalid date-time format. Use " + Constants.DATE_TIME_PATTERN, exception);
        }
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    public static int calculateAge(LocalDate dateOfBirth) {
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}
