package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "users", indexes = @Index(name = "idx_user_email", columnList = "email"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 150) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false, length = 120) private String fullName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Role role;
    @Column(nullable = false) @Builder.Default private boolean active = true;
    @Column(nullable = false, updatable = false) private Instant createdAt;
    private Instant updatedAt;
    @PrePersist void prePersist(){ createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void preUpdate(){ updatedAt = Instant.now(); }
}
