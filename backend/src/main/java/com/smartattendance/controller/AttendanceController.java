package com.smartattendance.controller;

import com.smartattendance.dto.AttendanceRequest;
import com.smartattendance.model.*;
import com.smartattendance.repository.*;
import com.smartattendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    private final StudentRepository students;
    private final CourseRepository courses;
    private final AttendanceSessionRepository sessions;
    private final AttendanceRecordRepository records;
    private final AttendanceService attendanceService;

    public AttendanceController(StudentRepository students, CourseRepository courses, AttendanceSessionRepository sessions,
                                AttendanceRecordRepository records, AttendanceService attendanceService) {
        this.students = students;
        this.courses = courses;
        this.sessions = sessions;
        this.records = records;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/students")
    public List<Student> students() { return students.findAll(); }

    @PostMapping("/students")
    public Student addStudent(@Valid @RequestBody Student student) { return students.save(student); }

    @GetMapping("/courses")
    public List<Course> courses() { return courses.findAll(); }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) { return courses.save(course); }

    @GetMapping("/sessions")
    public List<AttendanceSession> sessions() { return sessions.findAll(); }

    @PostMapping("/sessions")
    public AttendanceSession addSession(@RequestBody AttendanceSession session) { return sessions.save(session); }

    @GetMapping("/records")
    public List<AttendanceRecord> records() { return records.findAll(); }

    @PostMapping("/records")
    public AttendanceRecord mark(@Valid @RequestBody AttendanceRequest request) { return attendanceService.mark(request); }
}
