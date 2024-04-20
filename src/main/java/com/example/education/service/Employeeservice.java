package com.example.education.service;

import java.util.List;
import java.util.Optional;

import com.example.education.modal.Employee;

public interface Employeeservice {
     Employee saveEmployee(Employee employee);
     List<Employee> displayAllEmployee();
     Optional<Employee> findEmployeeById(Long id);
    //  Optional<Employee> findEmployeeByPhone(String phone);
     String updateEmployee(Employee employee);
    void deleteEmployee(Long id);
}
