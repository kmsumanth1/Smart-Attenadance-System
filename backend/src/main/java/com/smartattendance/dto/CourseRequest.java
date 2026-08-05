package com.smartattendance.dto;

import jakarta.validation.constraints.*;

public record CourseRequest(@NotBlank String code, @NotBlank String title, @NotBlank String department, @NotNull Long facultyId) {}
