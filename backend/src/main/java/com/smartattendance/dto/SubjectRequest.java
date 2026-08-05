package com.smartattendance.dto;

import jakarta.validation.constraints.*;

public record SubjectRequest(@NotBlank String code, @NotBlank String name, @Min(1) Integer credits, @NotNull Long courseId) {}
