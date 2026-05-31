package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;

public interface BillingStrategy {
    double calculateAmount(Appointment appointment);
}
