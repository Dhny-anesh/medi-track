package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> findById(String id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> listDoctors() {
        return doctorRepository.findAllByOrderByNameAsc();
    }

    public List<Doctor> listDoctorsByFee() {
        return doctorRepository.findAllByOrderByConsultationFeeAsc();
    }

    public List<Doctor> searchDoctors(String query) {
        if (query == null || query.isBlank()) {
            return listDoctors();
        }
        return doctorRepository.findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(query, query);
    }

    public boolean hasDoctors() {
        return doctorRepository.count() > 0;
    }
}
