package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(indexes = @Index(name = "idx_subject_code", columnList = "code"))
public class Subject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 30) private String code;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false) private Integer credits;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "course_id") private Course course;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true) @Builder.Default private List<AttendanceSession> sessions = new ArrayList<>();
}
