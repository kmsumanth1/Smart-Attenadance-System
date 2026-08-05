package com.smartattendance.dto;

import com.smartattendance.model.AttendanceStatus;
import java.time.Instant;

public record AttendanceRecordResponse(Long id, Long sessionId, Long studentId, String studentName, AttendanceStatus status, Instant markedAt, String remarks) {}
