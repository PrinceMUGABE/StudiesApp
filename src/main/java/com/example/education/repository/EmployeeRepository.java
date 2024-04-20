 package com.example.education.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
    // Employee findEmployeeByIdNo(String id);
    // Optional<Employee> findEmployeebyPhone(String phone);

}
