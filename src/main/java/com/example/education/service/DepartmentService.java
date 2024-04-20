package com.example.education.service;

import com.example.education.modal.Department;
import com.example.education.modal.School;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department saveDepartment(Department department);
    List<Department> getAllDepartments();
    String updateDepartment(Department department);
    String deleteDepartmentById(Long id);
    // List<Department> findDepartmentsByName(String name);
    Optional<Department> findDepartmentByName(String name);
    Optional<Department> findDepartmentById(Long id);
    Optional<Department> findDepartmentBySchool(Optional<School> school);
    List<Department> findDepartmentsBySchool(School school);
}
