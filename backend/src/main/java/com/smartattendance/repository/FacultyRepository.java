package com.smartattendance.repository;

import com.smartattendance.model.Faculty;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Page<Faculty> findByNameContainingIgnoreCaseOrEmployeeIdContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String employeeId, String department, Pageable pageable);
}
