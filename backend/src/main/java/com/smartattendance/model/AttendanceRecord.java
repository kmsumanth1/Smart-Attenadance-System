package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(uniqueConstraints = @UniqueConstraint(name = "uk_session_student", columnNames = {"session_id", "student_id"}))
public class AttendanceRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "session_id") private AttendanceSession session;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "student_id") private Student student;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private AttendanceStatus status;
    @Column(nullable = false) private Instant markedAt;
    @Column(length = 255) private String remarks;
    @PrePersist void prePersist(){ markedAt = Instant.now(); }
}
