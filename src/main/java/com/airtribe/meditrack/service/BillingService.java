package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BillingService {
    private final BillRepository billRepository;

    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Transactional
    public Bill payAppointmentBill(String appointmentId) {
        Bill bill = billRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Bill not found for appointment: " + appointmentId));
        bill.pay();
        return billRepository.save(bill);
    }
}
