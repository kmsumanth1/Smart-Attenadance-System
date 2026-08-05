package com.smartattendance.dto;

public record AttendanceSummaryResponse(Long studentId, String studentName, Long courseId, String courseTitle, Long subjectId, String subjectName, long totalSessions, long attendedSessions, double percentage) {}
