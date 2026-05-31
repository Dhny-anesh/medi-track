package com.airtribe.meditrack.constants;

public final class Constants {
    public static final String APP_TITLE = "MediTrack Clinic & Appointment Management";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_CURRENCY = "USD";
    public static final double STANDARD_APPOINTMENT_FEE = 120.00;
    public static final double DOCTOR_CONSULTATION_SURCHARGE = 30.00;
    public static final double TAX_RATE = 0.10;
    public static final String NEW_LINE = System.lineSeparator();

    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}
