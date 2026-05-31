package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Bill;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, String> {
    Optional<Bill> findByAppointmentId(String appointmentId);
}
