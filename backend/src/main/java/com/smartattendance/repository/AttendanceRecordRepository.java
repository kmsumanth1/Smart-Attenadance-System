package com.smartattendance.repository;

import com.smartattendance.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findBySessionIdAndStudentId(Long sessionId, Long studentId);
    Page<AttendanceRecord> findByStudentNameContainingIgnoreCaseOrSessionTopicContainingIgnoreCase(String student, String topic, Pageable pageable);
    long countByStudentId(Long studentId);
    long countByStudentIdAndStatusIn(Long studentId, java.util.Collection<AttendanceStatus> statuses);
    @Query("select r from AttendanceRecord r where (:studentId is null or r.student.id = :studentId) and (:sessionId is null or r.session.id = :sessionId)")
    Page<AttendanceRecord> filter(@Param("studentId") Long studentId, @Param("sessionId") Long sessionId, Pageable pageable);
}
