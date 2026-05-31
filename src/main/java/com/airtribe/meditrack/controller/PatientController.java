package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.CreatePatientRequest;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.IdGenerator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.listPatients();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        Patient patient = new Patient(
                IdGenerator.generateId("PAT"),
                request.name(),
                request.gender(),
                request.dateOfBirth(),
                request.email(),
                request.phoneNumber(),
                request.medicalHistory());
        Patient saved = patientService.addPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
