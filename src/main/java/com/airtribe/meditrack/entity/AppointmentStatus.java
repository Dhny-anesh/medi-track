package com.airtribe.meditrack.entity;

public enum AppointmentStatus {
    PENDING,
    CONFIRMED,
    SCHEDULED,
    COMPLETED,
    CANCELLED;

    @Override
    public String toString() {
        return name();
    }
}
