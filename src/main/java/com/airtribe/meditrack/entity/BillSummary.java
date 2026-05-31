package com.airtribe.meditrack.entity;

import java.util.Objects;

public final class BillSummary {
    private final String billId;
    private final String patientName;
    private final String doctorName;
    private final double amount;
    private final boolean paid;

    public BillSummary(String billId, String patientName, String doctorName, double amount, boolean paid) {
        this.billId = Objects.requireNonNull(billId, "billId cannot be null");
        this.patientName = Objects.requireNonNull(patientName, "patientName cannot be null");
        this.doctorName = Objects.requireNonNull(doctorName, "doctorName cannot be null");
        this.amount = amount;
        this.paid = paid;
    }

    public String getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return String.format("BillSummary[billId=%s, patient=%s, doctor=%s, amount=%.2f, paid=%s]",
                billId, patientName, doctorName, amount, paid);
    }
}
