package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(indexes = @Index(name = "idx_student_roll_number", columnList = "rollNumber"))
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 40) private String rollNumber;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false, length = 120) private String email;
    @Column(nullable = false, length = 80) private String department;
    @Column(nullable = false, length = 20) private String semester;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") private AppUser user;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true) @Builder.Default private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
}
