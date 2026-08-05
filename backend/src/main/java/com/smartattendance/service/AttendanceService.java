package com.smartattendance.service;

import com.smartattendance.dto.*;
import com.smartattendance.exception.ResourceNotFoundException;
import com.smartattendance.model.*;
import com.smartattendance.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final AttendanceSessionRepository sessions;
    private final AttendanceRecordRepository records;
    private final SubjectService subjects;
    private final FacultyService faculty;
    private final StudentService students;

    public AttendanceService(AttendanceSessionRepository sessions, AttendanceRecordRepository records, SubjectService subjects, FacultyService faculty, StudentService students) {
        this.sessions = sessions;
        this.records = records;
        this.subjects = subjects;
        this.faculty = faculty;
        this.students = students;
    }

    public Page<AttendanceSessionResponse> searchSessions(String q, Pageable p) {
        return (q == null || q.isBlank() ? sessions.findAll(p) : sessions.findByTopicContainingIgnoreCaseOrSessionDate(q, null, p)).map(this::toSessionResponse);
    }
    public AttendanceSessionResponse createSession(AttendanceSessionRequest r) { AttendanceSession s = new AttendanceSession(); applySession(s, r); return toSessionResponse(sessions.save(s)); }
    public AttendanceSessionResponse updateSession(Long id, AttendanceSessionRequest r) { AttendanceSession s = findSession(id); applySession(s, r); return toSessionResponse(sessions.save(s)); }
    public AttendanceSessionResponse getSession(Long id) { return toSessionResponse(findSession(id)); }
    public void deleteSession(Long id) { sessions.delete(findSession(id)); }

    public Page<AttendanceRecordDetailResponse> searchRecords(Long studentId, Long sessionId, Long courseId, Long subjectId, Pageable p) {
        return records.filter(studentId, sessionId, courseId, subjectId, p).map(this::toDetailResponse);
    }
    public AttendanceRecordDetailResponse mark(AttendanceRecordRequest r) { return toDetailResponse(saveRecord(r, records.findBySessionIdAndStudentId(r.sessionId(), r.studentId()).orElseGet(AttendanceRecord::new))); }
    public List<AttendanceRecordDetailResponse> markBulk(BulkAttendanceRequest request) { return request.records().stream().map(this::mark).toList(); }
    public AttendanceRecordDetailResponse updateRecord(Long id, AttendanceRecordRequest r) { return toDetailResponse(saveRecord(r, findRecord(id))); }
    public AttendanceRecordDetailResponse getRecord(Long id) { return toDetailResponse(findRecord(id)); }
    public void deleteRecord(Long id) { records.delete(findRecord(id)); }

    public AttendanceSummaryResponse studentSummary(Long studentId, Long courseId, Long subjectId) {
        Student student = students.find(studentId);
        long total = records.countFiltered(studentId, courseId, subjectId);
        long attended = records.countAttendedFiltered(studentId, courseId, subjectId, List.of(AttendanceStatus.PRESENT, AttendanceStatus.LATE));
        double pct = total == 0 ? 0.0 : Math.round((attended * 10000.0) / total) / 100.0;
        return new AttendanceSummaryResponse(student.getId(), student.getName(), courseId, null, subjectId, null, total, attended, pct);
    }

    public ReportResponse studentReport(Long studentId) {
        AttendanceSummaryResponse summary = studentSummary(studentId, null, null);
        return new ReportResponse(summary.studentId(), summary.studentName(), summary.totalSessions(), summary.attendedSessions(), summary.percentage());
    }

    public String exportCsv(Long studentId, Long courseId, Long subjectId) {
        String header = "Record ID,Student ID,Student Name,Course,Subject,Session Date,Topic,Status,Marked At,Remarks\n";
        return header + records.exportRows(studentId, courseId, subjectId).stream().map(r -> String.join(",",
                csv(r.getId()), csv(r.getStudent().getId()), csv(r.getStudent().getName()), csv(r.getSession().getSubject().getCourse().getTitle()),
                csv(r.getSession().getSubject().getName()), csv(r.getSession().getSessionDate()), csv(r.getSession().getTopic()), csv(r.getStatus()), csv(r.getMarkedAt()), csv(r.getRemarks()))).collect(Collectors.joining("\n"));
    }

    private void applySession(AttendanceSession s, AttendanceSessionRequest r) { s.setSubject(subjects.find(r.subjectId())); s.setFaculty(faculty.find(r.facultyId())); s.setSessionDate(r.sessionDate()); s.setTopic(r.topic()); }
    private AttendanceRecord saveRecord(AttendanceRecordRequest r, AttendanceRecord rec) { rec.setSession(findSession(r.sessionId())); rec.setStudent(students.find(r.studentId())); rec.setStatus(r.status()); rec.setRemarks(r.remarks()); return records.save(rec); }
    private AttendanceSession findSession(Long id) { return sessions.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance session", id)); }
    private AttendanceRecord findRecord(Long id) { return records.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance record", id)); }
    private AttendanceSessionResponse toSessionResponse(AttendanceSession s) { return new AttendanceSessionResponse(s.getId(), s.getSubject().getId(), s.getSubject().getName(), s.getFaculty().getId(), s.getFaculty().getName(), s.getSessionDate(), s.getTopic()); }
    private AttendanceRecordDetailResponse toDetailResponse(AttendanceRecord r) { Subject subject = r.getSession().getSubject(); Course course = subject.getCourse(); return new AttendanceRecordDetailResponse(r.getId(), r.getSession().getId(), r.getStudent().getId(), r.getStudent().getName(), course.getId(), course.getTitle(), subject.getId(), subject.getName(), r.getSession().getSessionDate(), r.getSession().getTopic(), r.getStatus(), r.getMarkedAt(), r.getRemarks()); }
    private String csv(Object value) { String text = value == null ? "" : value.toString(); return "\"" + text.replace("\"", "\"\"") + "\""; }
}
