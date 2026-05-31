package com.airtribe.meditrack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CreatePatientRequest(
        @NotBlank String name,
        @NotBlank String gender,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
        @NotBlank @Email String email,
        @NotBlank String phoneNumber,
        @NotNull List<String> medicalHistory) {
}
