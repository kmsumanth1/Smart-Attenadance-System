package com.smartattendance.controller;

import com.smartattendance.dto.LoginRequest;
import com.smartattendance.model.AppUser;
import com.smartattendance.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) {
        AppUser user = users.findByEmail(request.email()).orElseThrow();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return Map.of("id", user.getId(), "name", user.getFullName(), "email", user.getEmail(), "role", user.getRole());
    }
}
