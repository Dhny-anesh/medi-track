package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.contract.Payable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bills")
public class Bill implements Payable {
    @Id
    private String id;

    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id", unique = true)
    @JsonBackReference
    private Appointment appointment;

    private double amount;
    private LocalDate dueDate;
    private boolean paid;

    protected Bill() {
        // JPA requires a no-arg constructor
    }

    public Bill(String id, Appointment appointment, double amount) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.appointment = Objects.requireNonNull(appointment, "appointment cannot be null");
        this.amount = amount;
        this.dueDate = LocalDate.now().plusDays(14);
        this.paid = false;
    }

    public String getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void pay() {
        this.paid = true;
        appointment.complete();
    }

    @Override
    public double calculateAmount() {
        return amount;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return String.format("Bill[id=%s, appointmentId=%s, amount=%.2f, dueDate=%s, paid=%s]",
                id, appointment != null ? appointment.getId() : null, amount, dueDate, paid);
    }
}
