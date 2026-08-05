package com.smartattendance.dto;

public record StudentResponse(Long id, String rollNumber, String name, String email, String department, String semester, Long userId) {}
