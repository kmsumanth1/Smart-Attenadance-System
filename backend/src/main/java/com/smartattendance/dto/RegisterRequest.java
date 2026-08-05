package com.smartattendance.dto;

import com.smartattendance.model.Role;
import jakarta.validation.constraints.*;

public record RegisterRequest(@Email @NotBlank String email, @NotBlank @Size(min = 6) String password, @NotBlank String fullName, @NotNull Role role) {}
