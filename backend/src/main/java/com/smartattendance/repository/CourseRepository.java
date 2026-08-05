package com.smartattendance.repository;

import com.smartattendance.model.Course;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByTitleContainingIgnoreCaseOrCodeContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String title, String code, String department, Pageable pageable);
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
