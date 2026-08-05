package com.smartattendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BulkAttendanceRequest(@NotEmpty List<@Valid AttendanceRecordRequest> records) {}
