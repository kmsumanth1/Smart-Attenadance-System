package com.smartattendance.dto;

import com.smartattendance.model.Role;

public record AuthResponse(String token, String tokenType, Long expiresIn, Long id, String name, String email, Role role) {}
