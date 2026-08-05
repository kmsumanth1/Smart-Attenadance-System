package com.smartattendance.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AttendanceSessionRequest(@NotNull Long subjectId, @NotNull Long facultyId, @NotNull LocalDate sessionDate, @NotBlank String topic) {}
