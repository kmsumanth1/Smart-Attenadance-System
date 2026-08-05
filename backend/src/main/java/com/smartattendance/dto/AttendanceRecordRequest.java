package com.smartattendance.dto;

import com.smartattendance.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

public record AttendanceRecordRequest(@NotNull Long sessionId, @NotNull Long studentId, @NotNull AttendanceStatus status, String remarks) {}
