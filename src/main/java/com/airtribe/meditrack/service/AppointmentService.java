package com.airtribe.meditrack.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.AppointmentRepository;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.repository.PatientRepository;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.IdGenerator;

@Service
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Appointment scheduleAppointment(String doctorId, String patientId, LocalDateTime scheduledAt, String notes) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new InvalidDataException("Doctor not found: " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new InvalidDataException("Patient not found: " + patientId));
        if (scheduledAt == null || !DateUtil.isFuture(scheduledAt)) {
            throw new InvalidDataException("Appointment must be scheduled for a future date/time");
        }

        Appointment appointment = new Appointment(IdGenerator.generateId("APPT"), doctor, patient, scheduledAt, notes);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> listAppointments() {
        return appointmentRepository.findAllByOrderByScheduledAtAsc();
    }

    public Optional<Appointment> findById(String id) {
        return appointmentRepository.findById(id);
    }

    @Transactional
    public void cancelAppointment(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + id));
        appointment.cancel();
        appointmentRepository.save(appointment);
    }

    public List<Appointment> searchAppointments(String query) {
        if (query == null || query.isBlank()) {
            return listAppointments();
        }

        List<Appointment> results = new ArrayList<>(
                appointmentRepository.findByDoctorNameContainingIgnoreCaseOrPatientNameContainingIgnoreCase(query, query)
        );

        try {
            AppointmentStatus status = AppointmentStatus.valueOf(query.trim().toUpperCase());
            appointmentRepository.findAllByStatus(status).stream()
                    .filter(appointment -> !results.contains(appointment))
                    .forEach(results::add);
        } catch (IllegalArgumentException ignored) {
            // query is not a valid status, ignore
        }

        return results;
    }

    public List<Appointment> listByDoctor(String doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId);
    }

    public List<Appointment> listByPatient(String patientId) {
        return appointmentRepository.findAllByPatientId(patientId);
    }
}
