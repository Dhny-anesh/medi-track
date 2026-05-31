package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {
    List<Patient> findAllByOrderByNameAsc();

    List<Patient> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
            String name, String email, String phoneNumber);
}
