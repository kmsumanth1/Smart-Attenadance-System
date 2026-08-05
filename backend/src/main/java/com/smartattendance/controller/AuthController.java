package com.smartattendance.controller;

import com.smartattendance.dto.*;
import com.smartattendance.model.AppUser;
import com.smartattendance.repository.AppUserRepository;
import com.smartattendance.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AppUserRepository users, PasswordEncoder encoder, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, JwtService jwtService) {
        this.users = users;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        users.findByEmail(request.email()).ifPresent(user -> { throw new IllegalArgumentException("Email already registered"); });
        AppUser user = AppUser.builder().email(request.email()).password(encoder.encode(request.password()))
                .fullName(request.fullName()).role(request.role()).active(true).build();
        return tokenResponse(users.save(user));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        AppUser user = users.findByEmail(request.email()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        return tokenResponse(user);
    }

    private AuthResponse tokenResponse(AppUser user) {
        var details = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(details, Map.of("role", user.getRole().name(), "name", user.getFullName(), "id", user.getId()));
        return new AuthResponse(token, "Bearer", jwtService.expirationSeconds(), user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
