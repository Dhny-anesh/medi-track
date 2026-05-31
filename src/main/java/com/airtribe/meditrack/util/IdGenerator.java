package com.airtribe.meditrack.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class IdGenerator {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private IdGenerator() {
        throw new UnsupportedOperationException("IdGenerator cannot be instantiated");
    }

    public static String generateId(String prefix) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("%s-%s-%s", prefix, timestamp, randomSuffix);
    }
}
