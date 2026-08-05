package com.smartattendance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AttendanceSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Course course;
    private LocalDate sessionDate;
    private String topic;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
