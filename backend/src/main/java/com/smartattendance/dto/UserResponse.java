package com.smartattendance.dto;

import com.smartattendance.model.Role;

public record UserResponse(Long id, String email, String fullName, Role role, boolean active) {}
