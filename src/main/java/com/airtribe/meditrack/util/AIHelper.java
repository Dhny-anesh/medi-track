package com.airtribe.meditrack.util;

public final class AIHelper {
    private AIHelper() {
        throw new UnsupportedOperationException("AIHelper cannot be instantiated");
    }

    public static String getOptimizationTip() {
        return "Use MediTrack reports to track appointment volume, bill payment status, and provider capacity.";
    }

    public static String getPatientCareTip() {
        return "Maintain accurate medical history and follow up on incomplete bills to improve patient care continuity.";
    }
}
