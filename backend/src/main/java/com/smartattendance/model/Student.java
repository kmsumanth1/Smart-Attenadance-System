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

@Entity
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String rollNumber;
    @Column(nullable = false)
    private String name;
    private String department;
    private String semester;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}
