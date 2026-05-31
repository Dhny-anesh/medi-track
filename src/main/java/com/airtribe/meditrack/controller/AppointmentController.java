package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.CreateAppointmentRequest;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final BillingService billingService;

    public AppointmentController(AppointmentService appointmentService, BillingService billingService) {
        this.appointmentService = appointmentService;
        this.billingService = billingService;
    }

    @GetMapping
    public List<Appointment> getAppointments() {
        return appointmentService.listAppointments();
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = appointmentService.scheduleAppointment(
                request.doctorId(),
                request.patientId(),
                request.scheduledAt(),
                request.notes());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable String id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Appointment> payAppointment(@PathVariable String id) {
        billingService.payAppointmentBill(id);
        return ResponseEntity.ok(appointmentService.findById(id).orElseThrow());
    }
}
