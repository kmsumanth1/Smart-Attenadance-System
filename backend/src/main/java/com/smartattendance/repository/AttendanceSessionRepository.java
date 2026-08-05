package com.smartattendance.repository;

import com.smartattendance.model.AttendanceSession;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    Page<AttendanceSession> findByTopicContainingIgnoreCaseOrSessionDate(String topic, LocalDate date, Pageable pageable);
}
