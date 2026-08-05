package com.smartattendance.dto;

import jakarta.validation.constraints.*;

public record StudentRequest(@NotBlank String rollNumber, @NotBlank String name, @Email @NotBlank String email, @NotBlank String department, @NotBlank String semester, Long userId) {}
