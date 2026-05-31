package com.airtribe.meditrack.config;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.IdGenerator;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "meditrack.seed.enabled", havingValue = "true")
public class DatabaseSeeder implements CommandLineRunner {
    private final DoctorService doctorService;
    private final PatientService patientService;

    public DatabaseSeeder(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Override
    public void run(String... args) {
        seedDoctors();
        seedPatients();
        System.out.println(Constants.APP_TITLE + " REST API is running. Browse /swagger-ui.html to explore endpoints.");
    }

    private void seedDoctors() {
        if (doctorService.hasDoctors()) {
            return;
        }

        doctorService.addDoctor(new Doctor(
                IdGenerator.generateId("DOC"),
                "Aisha Smith",
                "Female",
                LocalDate.of(1981, 5, 15),
                "aisha.smith@meditrack.com",
                "+1 555 0123",
                Specialization.GENERAL_MEDICINE,
                Constants.STANDARD_APPOINTMENT_FEE));
        doctorService.addDoctor(new Doctor(
                IdGenerator.generateId("DOC"),
                "Wei Chen",
                "Male",
                LocalDate.of(1975, 10, 21),
                "wei.chen@meditrack.com",
                "+1 555 0456",
                Specialization.PEDIATRICS,
                Constants.STANDARD_APPOINTMENT_FEE + 20));
    }

    private void seedPatients() {
        if (patientService.hasPatients()) {
            return;
        }

        patientService.addPatient(new Patient(
                IdGenerator.generateId("PAT"),
                "Mia Johnson",
                "Female",
                LocalDate.of(1993, 3, 10),
                "mia.johnson@example.com",
                "+1 555 0789",
                List.of("Asthma", "Seasonal allergies")));
        patientService.addPatient(new Patient(
                IdGenerator.generateId("PAT"),
                "Ethan Carter",
                "Male",
                LocalDate.of(2000, 12, 2),
                "ethan.carter@example.com",
                "+1 555 0102",
                List.of("Minor back pain")));
    }
}
