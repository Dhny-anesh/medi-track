package com.airtribe.meditrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {
    private String specialization;
    private double consultationFee;

    protected Doctor() {
        super();
    }

    public Doctor(String id, String name, String gender, LocalDate dateOfBirth, String email, String phoneNumber,
                  Specialization specialization, double consultationFee) {
        super(id, name, gender, dateOfBirth, email, phoneNumber);
        this.specialization = Objects.requireNonNull(specialization, "specialization cannot be null").getLabel();
        this.consultationFee = consultationFee;
    }

    public String getSpecialization() {
        return specialization;
    }

    public static Comparator<Doctor> feeComparator() {
        return Comparator.comparingDouble(Doctor::getConsultationFee);
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.isBlank()) {
            return false;
        }
        String lower = query.toLowerCase();
        if (super.matchesQuery(query)) {
            return true;
        }
        return specialization.toString().toLowerCase().contains(lower);
    }

    @Override
    public String getIdentifier() {
        return getId();
    }

    @Override
    public String toString() {
        return String.format("Doctor[id=%s, name=%s, specialization=%s, fee=%.2f]",
                getId(), getName(), specialization, consultationFee);
    }
}
