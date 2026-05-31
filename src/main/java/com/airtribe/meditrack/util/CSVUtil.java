package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CSVUtil {
    private CSVUtil() {
        throw new UnsupportedOperationException("CSVUtil cannot be instantiated");
    }

    public static String toCsvDoctors(List<Doctor> doctors) {
        String header = "Doctor ID,Name,Gender,DOB,Email,Phone,Specialization,Consultation Fee";
        return doctors.stream()
            .map(doc -> String.format("%s,%s,%s,%s,%s,%s,%s,%.2f",
                doc.getId(), doc.getName(), doc.getGender(), doc.getDateOfBirth(), doc.getEmail(),
                doc.getPhoneNumber(), doc.getSpecialization(), doc.getConsultationFee()))
                .collect(Collectors.joining(System.lineSeparator(), header + System.lineSeparator(), ""));
    }

    public static String toCsvPatients(List<Patient> patients) {
        String header = "Patient ID,Name,Gender,DOB,Email,Phone,Medical History";
        return patients.stream()
            .map(patient -> String.format("%s,%s,%s,%s,%s,%s,%s",
                patient.getId(), patient.getName(), patient.getGender(), patient.getDateOfBirth(),
                patient.getEmail(), patient.getPhoneNumber(), String.join(";", patient.getMedicalHistory())))
                .collect(Collectors.joining(System.lineSeparator(), header + System.lineSeparator(), ""));
    }

    public static String toCsvAppointments(List<Appointment> appointments) {
        String header = "Appointment ID,Doctor ID,Patient ID,Scheduled At,Status,Amount,Paid,Notes";
        return appointments.stream()
            .map(appointment -> String.format("%s,%s,%s,%s,%s,%.2f,%s,%s",
                appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(),
                appointment.getScheduledAt(), appointment.getStatus(), appointment.getBill().calculateAmount(),
                appointment.getBill().isPaid(), appointment.getNotes().replace(",", " ")))
                .collect(Collectors.joining(System.lineSeparator(), header + System.lineSeparator(), ""));
    }

    public static void writeToFile(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }

    public static List<Doctor> parseDoctors(List<String> lines) {
        List<Doctor> doctors = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 8) {
                continue;
            }
            Specialization specialization = Specialization.valueOf(parts[6].toUpperCase().replace(' ', '_'));
            doctors.add(new Doctor(parts[0], parts[1], parts[2], LocalDate.parse(parts[3]), parts[4], parts[5], specialization, Double.parseDouble(parts[7])));
        }
        return doctors;
    }

    public static List<Patient> parsePatients(List<String> lines) {
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 7) {
                continue;
            }
            List<String> history = parts[6].isBlank() ? List.of() : List.of(parts[6].split(";"));
            patients.add(new Patient(parts[0], parts[1], parts[2], LocalDate.parse(parts[3]), parts[4], parts[5], history));
        }
        return patients;
    }

    public static List<Appointment> parseAppointments(List<String> lines, DoctorService doctorService, PatientService patientService) {
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 8) {
                continue;
            }
            doctorService.findById(parts[1]).ifPresent(doctor ->
                    patientService.findById(parts[2]).ifPresent(patient -> {
                        Appointment appointment = new Appointment(parts[0], doctor, patient, LocalDateTime.parse(parts[3]), parts[7]);
                        AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(parts[4]);
                        if (appointmentStatus == AppointmentStatus.CANCELLED) {
                            appointment.cancel();
                        } else if (Boolean.parseBoolean(parts[6])) {
                            appointment.getBill().pay();
                        } else if (appointmentStatus == AppointmentStatus.COMPLETED) {
                            appointment.complete();
                        }
                        appointments.add(appointment);
                    })
            );
        }
        return appointments;
    }
}
