package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.CreateDoctorRequest;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.util.IdGenerator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.listDoctors();
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        Doctor doctor = new Doctor(
                IdGenerator.generateId("DOC"),
                request.name(),
                request.gender(),
                request.dateOfBirth(),
                request.email(),
                request.phoneNumber(),
                Specialization.fromLabel(request.specialization()),
                request.consultationFee());
        Doctor saved = doctorService.addDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
