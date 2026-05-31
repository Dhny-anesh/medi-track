package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.contract.Searchable;
import com.airtribe.meditrack.util.BillFactory;
import com.airtribe.meditrack.util.BillingStrategy;
import com.airtribe.meditrack.util.StandardBillingStrategy;
import com.airtribe.meditrack.util.IdGenerator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointment implements Searchable, Cloneable {
    @Id
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime scheduledAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Bill bill;

    @jakarta.persistence.Transient
    private BillingStrategy billingStrategy;

    protected Appointment() {
        // JPA requires a no-arg constructor
    }

    public Appointment(String id, Doctor doctor, Patient patient, LocalDateTime scheduledAt, String notes) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.doctor = Objects.requireNonNull(doctor, "doctor cannot be null");
        this.patient = Objects.requireNonNull(patient, "patient cannot be null");
        this.scheduledAt = Objects.requireNonNull(scheduledAt, "scheduledAt cannot be null");
        this.notes = notes == null ? "" : notes;
        this.status = AppointmentStatus.SCHEDULED;
        this.billingStrategy = new StandardBillingStrategy();
        this.bill = generateBill();
    }

    protected Appointment(String id, Doctor doctor, Patient patient, LocalDateTime scheduledAt, String notes, BillingStrategy billingStrategy) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.doctor = Objects.requireNonNull(doctor, "doctor cannot be null");
        this.patient = Objects.requireNonNull(patient, "patient cannot be null");
        this.scheduledAt = Objects.requireNonNull(scheduledAt, "scheduledAt cannot be null");
        this.notes = notes == null ? "" : notes;
        this.status = AppointmentStatus.SCHEDULED;
        this.billingStrategy = billingStrategy == null ? new StandardBillingStrategy() : billingStrategy;
        this.bill = generateBill();
    }

    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public String getNotes() {
        return notes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public Bill getBill() {
        return bill;
    }

    public double calculateEstimatedAmount() {
        BillingStrategy strategy = billingStrategy == null ? new StandardBillingStrategy() : billingStrategy;
        return strategy.calculateAmount(this);
    }

    protected Bill generateBill() {
        Bill bill = BillFactory.createBill(id + "-BILL", this, billingStrategy);
        bill.setAppointment(this);
        return bill;
    }

    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    @Override
    public Appointment clone() {
        Patient clonedPatient = patient.clone();
        Appointment cloned = new Appointment(IdGenerator.generateId("APPT"), doctor, clonedPatient, scheduledAt, notes, billingStrategy);
        cloned.status = status;
        if (bill.isPaid()) {
            cloned.getBill().pay();
        }
        return cloned;
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.isBlank()) {
            return false;
        }
        String lower = query.toLowerCase();
        return getId().toLowerCase().contains(lower)
            || doctor.getName().toLowerCase().contains(lower)
            || patient.getName().toLowerCase().contains(lower)
            || status.name().toLowerCase().contains(lower);
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Appointment[id=%s, doctor=%s, patient=%s, scheduledAt=%s, status=%s, amount=%.2f]",
                id, doctor.getName(), patient.getName(), scheduledAt, status, bill.calculateAmount());
    }
}
