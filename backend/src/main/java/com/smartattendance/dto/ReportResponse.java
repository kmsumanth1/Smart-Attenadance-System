package com.smartattendance.dto;

public record ReportResponse(Long studentId, String studentName, long totalSessions, long presentSessions, double attendancePercentage) {}
