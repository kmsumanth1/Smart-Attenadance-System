package com.smartattendance.dto;

import jakarta.validation.constraints.*;

public record FacultyRequest(@NotBlank String employeeId, @NotBlank String name, @Email @NotBlank String email, @NotBlank String department, Long userId) {}
