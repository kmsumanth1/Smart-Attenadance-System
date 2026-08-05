package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(indexes = @Index(name = "idx_session_date", columnList = "sessionDate"))
public class AttendanceSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "subject_id") private Subject subject;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id") private Faculty faculty;
    @Column(nullable = false) private LocalDate sessionDate;
    @Column(nullable = false, length = 160) private String topic;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true) @Builder.Default private List<AttendanceRecord> records = new ArrayList<>();
}
