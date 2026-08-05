package com.smartattendance.controller;

import com.smartattendance.dto.*;import com.smartattendance.model.AppUser;import com.smartattendance.repository.AppUserRepository;import jakarta.validation.Valid;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController{private final AppUserRepository users;private final PasswordEncoder encoder;public AuthController(AppUserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}@PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody LoginRequest r){AppUser u=users.findByEmail(r.email()).orElseThrow(()->new IllegalArgumentException("Invalid credentials"));if(!u.isActive()||!encoder.matches(r.password(),u.getPassword()))throw new IllegalArgumentException("Invalid credentials");return Map.of("id",u.getId(),"name",u.getFullName(),"email",u.getEmail(),"role",u.getRole());}}
