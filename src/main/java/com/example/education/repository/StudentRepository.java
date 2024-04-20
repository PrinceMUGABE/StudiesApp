package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.education.modal.Student;
import com.example.education.modal.User;
import java.util.Optional;



public interface StudentRepository extends JpaRepository<Student, Long>{
    Student findStudentByStudentCode(String code);
   
    Optional<Student> findStudentByEmail(String email);
}