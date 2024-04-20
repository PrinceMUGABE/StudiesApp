package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.Department;
import com.example.education.modal.School;

import java.util.Optional;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
    Optional<Department> findDepartmentByName(String departmentName);
    Optional<Department> findDepartmentBySchool(Optional<School> school);
    List<Department> findDepartmentsBySchool(School school);
}
                                    