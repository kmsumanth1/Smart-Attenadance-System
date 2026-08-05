package com.smartattendance.controller;

import com.smartattendance.dto.*;
import com.smartattendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service = service; }

    @GetMapping("/sessions")
    Page<AttendanceSessionResponse> sessions(@RequestParam(required = false) String q, Pageable p) { return service.searchSessions(q, p); }
    @GetMapping("/sessions/{id}")
    AttendanceSessionResponse session(@PathVariable Long id) { return service.getSession(id); }
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    AttendanceSessionResponse createSession(@Valid @RequestBody AttendanceSessionRequest r) { return service.createSession(r); }
    @PutMapping("/sessions/{id}")
    AttendanceSessionResponse updateSession(@PathVariable Long id, @Valid @RequestBody AttendanceSessionRequest r) { return service.updateSession(id, r); }
    @DeleteMapping("/sessions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSession(@PathVariable Long id) { service.deleteSession(id); }

    @GetMapping("/records")
    Page<AttendanceRecordDetailResponse> records(@RequestParam(required = false) Long studentId, @RequestParam(required = false) Long sessionId,
                                                 @RequestParam(required = false) Long courseId, @RequestParam(required = false) Long subjectId, Pageable p) {
        return service.searchRecords(studentId, sessionId, courseId, subjectId, p);
    }
    @GetMapping("/records/{id}")
    AttendanceRecordDetailResponse record(@PathVariable Long id) { return service.getRecord(id); }
    @PostMapping("/records")
    @ResponseStatus(HttpStatus.CREATED)
    AttendanceRecordDetailResponse mark(@Valid @RequestBody AttendanceRecordRequest r) { return service.mark(r); }
    @PostMapping("/records/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    java.util.List<AttendanceRecordDetailResponse> markBulk(@Valid @RequestBody BulkAttendanceRequest r) { return service.markBulk(r); }
    @PutMapping("/records/{id}")
    AttendanceRecordDetailResponse updateRecord(@PathVariable Long id, @Valid @RequestBody AttendanceRecordRequest r) { return service.updateRecord(id, r); }
    @DeleteMapping("/records/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRecord(@PathVariable Long id) { service.deleteRecord(id); }

    @GetMapping("/summary/students/{studentId}")
    AttendanceSummaryResponse studentSummary(@PathVariable Long studentId, @RequestParam(required = false) Long courseId, @RequestParam(required = false) Long subjectId) {
        return service.studentSummary(studentId, courseId, subjectId);
    }
    @GetMapping("/reports/students/{studentId}")
    ReportResponse studentReport(@PathVariable Long studentId) { return service.studentReport(studentId); }
    @GetMapping(value = "/reports/export", produces = "text/csv")
    ResponseEntity<String> export(@RequestParam(required = false) Long studentId, @RequestParam(required = false) Long courseId, @RequestParam(required = false) Long subjectId) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance-report.csv").body(service.exportCsv(studentId, courseId, subjectId));
    }
}
