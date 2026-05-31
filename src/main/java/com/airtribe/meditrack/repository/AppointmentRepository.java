package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findAllByOrderByScheduledAtAsc();

    List<Appointment> findAllByDoctorId(String doctorId);

    List<Appointment> findAllByPatientId(String patientId);

    List<Appointment> findByDoctorNameContainingIgnoreCaseOrPatientNameContainingIgnoreCase(String doctorName, String patientName);

    List<Appointment> findAllByStatus(AppointmentStatus status);
}
