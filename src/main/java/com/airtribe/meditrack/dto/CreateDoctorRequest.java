package com.airtribe.meditrack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CreateDoctorRequest(
        @NotBlank String name,
        @NotBlank String gender,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
        @NotBlank @Email String email,
        @NotBlank String phoneNumber,
        @NotBlank String specialization,
        @Positive double consultationFee) {
}
