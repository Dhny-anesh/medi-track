package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patients")
public class Patient extends Person implements Cloneable {
    @Convert(converter = StringListConverter.class)
    @Column(name = "medical_history", columnDefinition = "TEXT")
    private List<String> medicalHistory;

    protected Patient() {
        super();
    }

    public Patient(String id, String name, String gender, LocalDate dateOfBirth, String email, String phoneNumber,
                   List<String> medicalHistory) {
        super(id, name, gender, dateOfBirth, email, phoneNumber);
        this.medicalHistory = medicalHistory == null ? Collections.emptyList() : List.copyOf(medicalHistory);
    }

    public List<String> getMedicalHistory() {
        return medicalHistory == null ? Collections.emptyList() : List.copyOf(medicalHistory);
    }

    @Override
    public boolean matchesQuery(String query) {
        if (super.matchesQuery(query)) {
            return true;
        }
        if (query == null || query.isBlank()) {
            return false;
        }
        String lower = query.toLowerCase();
        return getName().toLowerCase().contains(lower)
                || getId().toLowerCase().contains(lower)
                || getMedicalHistory().stream().anyMatch(entry -> entry.toLowerCase().contains(lower));
    }

    @Override
    public String getIdentifier() {
        return getId();
    }

    @Override
    public String toString() {
        return String.format("Patient[id=%s, name=%s, age=%d, history=%s]",
                getId(), getName(), getAge(), getMedicalHistory());
    }

    @Override
    public Patient clone() {
        return new Patient(getId(), getName(), getGender(), getDateOfBirth(), getEmail(), getPhoneNumber(),
                List.copyOf(getMedicalHistory()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        return getId().equals(patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
