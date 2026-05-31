package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import java.util.Objects;

public final class BillFactory {
    private BillFactory() {
        throw new UnsupportedOperationException("BillFactory cannot be instantiated");
    }

    public static Bill createBill(String billId, Appointment appointment, BillingStrategy strategy) {
        Objects.requireNonNull(billId, "billId cannot be null");
        Objects.requireNonNull(appointment, "appointment cannot be null");
        Objects.requireNonNull(strategy, "strategy cannot be null");
        double amount = strategy.calculateAmount(appointment);
        return new Bill(billId, appointment, amount);
    }
}
