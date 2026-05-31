CREATE TABLE bills (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    appointment_id VARCHAR(50) NOT NULL UNIQUE,
    amount DOUBLE PRECISION NOT NULL,
    due_date DATE NOT NULL,
    paid BOOLEAN NOT NULL,
    CONSTRAINT fk_bills_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
