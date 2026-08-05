package com.smartattendance.repository;

import com.smartattendance.model.Student;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByNameContainingIgnoreCaseOrRollNumberContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String roll, String department, Pageable pageable);
}
