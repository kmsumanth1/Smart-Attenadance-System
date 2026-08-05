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

    @Query("""
            select r from AttendanceRecord r
            where (:studentId is null or r.student.id = :studentId)
              and (:sessionId is null or r.session.id = :sessionId)
              and (:courseId is null or r.session.subject.course.id = :courseId)
              and (:subjectId is null or r.session.subject.id = :subjectId)
            """)
    Page<AttendanceRecord> filter(@Param("studentId") Long studentId, @Param("sessionId") Long sessionId,
                                  @Param("courseId") Long courseId, @Param("subjectId") Long subjectId, Pageable pageable);

    @Query("""
            select r from AttendanceRecord r
            where (:studentId is null or r.student.id = :studentId)
              and (:courseId is null or r.session.subject.course.id = :courseId)
              and (:subjectId is null or r.session.subject.id = :subjectId)
            order by r.session.sessionDate desc
            """)
    java.util.List<AttendanceRecord> exportRows(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("subjectId") Long subjectId);

    @Query("""
            select count(r) from AttendanceRecord r
            where r.student.id = :studentId
              and (:courseId is null or r.session.subject.course.id = :courseId)
              and (:subjectId is null or r.session.subject.id = :subjectId)
            """)
    long countFiltered(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("subjectId") Long subjectId);

    @Query("""
            select count(r) from AttendanceRecord r
            where r.student.id = :studentId and r.status in :statuses
              and (:courseId is null or r.session.subject.course.id = :courseId)
              and (:subjectId is null or r.session.subject.id = :subjectId)
            """)
    long countAttendedFiltered(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("subjectId") Long subjectId,
                               @Param("statuses") java.util.Collection<AttendanceStatus> statuses);
}
