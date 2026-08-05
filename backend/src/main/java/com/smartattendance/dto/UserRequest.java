package com.smartattendance.dto;

import com.smartattendance.model.Role;
import jakarta.validation.constraints.*;

public record UserRequest(@NotBlank @Email String email, @NotBlank @Size(min = 6) String password, @NotBlank String fullName, @NotNull Role role, Boolean active) {}
