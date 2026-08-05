package com.smartattendance.dto;

import java.time.LocalDate;

public record AttendanceSessionResponse(Long id, Long subjectId, String subjectName, Long facultyId, String facultyName, LocalDate sessionDate, String topic) {}
