package com.smartattendance.service;

import com.smartattendance.dto.AttendanceRequest;
import com.smartattendance.model.AttendanceRecord;
import com.smartattendance.repository.AttendanceRecordRepository;
import com.smartattendance.repository.AttendanceSessionRepository;
import com.smartattendance.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private final AttendanceRecordRepository records;
    private final AttendanceSessionRepository sessions;
    private final StudentRepository students;

    public AttendanceService(AttendanceRecordRepository records, AttendanceSessionRepository sessions, StudentRepository students) {
        this.records = records;
        this.sessions = sessions;
        this.students = students;
    }

    public AttendanceRecord mark(AttendanceRequest request) {
        AttendanceRecord record = new AttendanceRecord();
        record.setSession(sessions.findById(request.sessionId()).orElseThrow());
        record.setStudent(students.findById(request.studentId()).orElseThrow());
        record.setStatus(request.status());
        return records.save(record);
    }
}
