package com.airtribe.meditrack.util;

import java.nio.file.Path;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Path dataFolder;
    private final Path doctorCsv;
    private final Path patientCsv;
    private final Path appointmentCsv;

    static {
        System.out.println("AppConfig static initialization complete");
    }

    private AppConfig() {
        this.dataFolder = Path.of("data");
        this.doctorCsv = dataFolder.resolve("doctors.csv");
        this.patientCsv = dataFolder.resolve("patients.csv");
        this.appointmentCsv = dataFolder.resolve("appointments.csv");
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDoctorCsv() {
        return doctorCsv;
    }

    public Path getPatientCsv() {
        return patientCsv;
    }

    public Path getAppointmentCsv() {
        return appointmentCsv;
    }
}
