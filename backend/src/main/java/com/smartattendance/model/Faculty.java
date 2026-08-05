package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(indexes = @Index(name = "idx_faculty_employee_id", columnList = "employeeId"))
public class Faculty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 40) private String employeeId;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false, length = 120) private String email;
    @Column(nullable = false, length = 80) private String department;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") private AppUser user;
    @OneToMany(mappedBy = "faculty") @Builder.Default private List<Course> courses = new ArrayList<>();
}
