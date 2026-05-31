package com.airtribe.meditrack.entity;

import java.util.Arrays;

public enum Specialization {
    GENERAL_MEDICINE("General Medicine"),
    PEDIATRICS("Pediatrics"),
    DERMATOLOGY("Dermatology"),
    CARDIOLOGY("Cardiology"),
    ORTHOPEDICS("Orthopedics"),
    PSYCHIATRY("Psychiatry"),
    PHYSIOTHERAPY("Physiotherapy");

    private final String label;

    Specialization(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Specialization fromLabel(String label) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Specialization is required");
        }
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(label) || value.label.equalsIgnoreCase(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported specialization: " + label));
    }

    @Override
    public String toString() {
        return label;
    }
}
