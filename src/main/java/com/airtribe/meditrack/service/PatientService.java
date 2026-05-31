package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> findById(String id) {
        return patientRepository.findById(id);
    }

    public List<Patient> listPatients() {
        return patientRepository.findAllByOrderByNameAsc();
    }

    public List<Patient> searchPatients(String query) {
        if (query == null || query.isBlank()) {
            return listPatients();
        }
        return patientRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(query, query, query);
    }

    public boolean hasPatients() {
        return patientRepository.count() > 0;
    }
}
