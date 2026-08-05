package com.smartattendance.dto;

import com.smartattendance.model.AttendanceStatus;
import java.time.Instant;
import java.time.LocalDate;

public record AttendanceRecordDetailResponse(Long id, Long sessionId, Long studentId, String studentName, Long courseId, String courseTitle, Long subjectId, String subjectName, LocalDate sessionDate, String topic, AttendanceStatus status, Instant markedAt, String remarks) {}
