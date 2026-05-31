package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.constants.Constants;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double calculateAmount(Appointment appointment) {
        return appointment.getDoctor().getConsultationFee() + Constants.DOCTOR_CONSULTATION_SURCHARGE;
    }
}
