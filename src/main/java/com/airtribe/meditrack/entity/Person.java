package com.airtribe.meditrack.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@MappedSuperclass
public abstract class Person extends MedicalEntity {
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;

    protected Person() {
        // JPA requires a no-arg constructor
    }

    protected Person(String id, String name, String gender, LocalDate dateOfBirth, String email, String phoneNumber) {
        super(id, name);
        this.gender = Objects.requireNonNull(gender, "gender cannot be null");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "dateOfBirth cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s, gender=%s, age=%d, email=%s, phone=%s]",
                getClass().getSimpleName(), getId(), getName(), gender, getAge(), email, phoneNumber);
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
        return gender.toLowerCase().contains(lower)
                || email.toLowerCase().contains(lower)
                || phoneNumber.toLowerCase().contains(lower);
    }
}
