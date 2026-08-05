package com.smartattendance.model;

import jakarta.persistence.*;

@Entity
public class AttendanceRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private AttendanceSession session;
    @ManyToOne(optional = false)
    private Student student;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AttendanceSession getSession() { return session; }
    public void setSession(AttendanceSession session) { this.session = session; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
}
