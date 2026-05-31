package com.airtribe.meditrack.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.airtribe.meditrack.Main;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillingService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.IdGenerator;

public class TestRunner {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        application.setAdditionalProfiles("h2");

        try (ConfigurableApplicationContext context = application.run(args)) {
            DoctorService doctorService = context.getBean(DoctorService.class);
            PatientService patientService = context.getBean(PatientService.class);
            AppointmentService appointmentService = context.getBean(AppointmentService.class);
            BillingService billingService = context.getBean(BillingService.class);

            Doctor doctor = new Doctor(IdGenerator.generateId("DOC"), "Noah Patel", "Male", LocalDate.of(1984, 2, 9),
                    "noah.patel@example.com", "+1 555 1222", Specialization.DERMATOLOGY, 150.0);
            Patient patient = new Patient(IdGenerator.generateId("PAT"), "Zara Lee", "Female", LocalDate.of(1990, 9, 18),
                    "zara.lee@example.com", "+1 555 1333", List.of("Eczema"));

            doctorService.addDoctor(doctor);
            patientService.addPatient(patient);

            System.out.println("Testing MediTrack manual runner...");
            System.out.println("Doctor count: " + doctorService.listDoctors().size());
            System.out.println("Patient count: " + patientService.listPatients().size());

            LocalDateTime scheduledAt = LocalDateTime.now().plusDays(2);
            Appointment appointment = appointmentService.scheduleAppointment(doctor.getId(), patient.getId(),
                    scheduledAt, "Initial consultation");

            boolean billNotPaid = !appointment.getBill().isPaid();
            System.out.println("Scheduled appointment: " + appointment);
            System.out.println("Bill status before payment: " + billNotPaid);

            billingService.payAppointmentBill(appointment.getId());
            Appointment paidAppointment = appointmentService.findById(appointment.getId()).orElseThrow();
            System.out.println("Bill status after payment: " + paidAppointment.getBill().isPaid());

            System.out.println("Search doctors by 'Noah': " + doctorService.searchDoctors("Noah"));
            System.out.println("Search appointments by status 'SCHEDULED': " + appointmentService.searchAppointments("SCHEDULED"));
        }
    }
}
