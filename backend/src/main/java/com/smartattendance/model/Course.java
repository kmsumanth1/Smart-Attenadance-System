package com.smartattendance.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(indexes = @Index(name = "idx_course_code", columnList = "code"))
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 30) private String code;
    @Column(nullable = false, length = 120) private String title;
    @Column(nullable = false, length = 80) private String department;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id") private Faculty faculty;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true) @Builder.Default private List<Subject> subjects = new ArrayList<>();
}
