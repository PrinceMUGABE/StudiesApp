package com.example.education.service;

import java.util.List;
import java.util.Optional;
import com.example.education.modal.Student;
import com.example.education.modal.User;

public interface StudentService {
    Student saveStudent(Student student);
    List<Student> displayAllStudents();
    Optional<Student> findStudentById(Long id);
    Student findStudentByCode(String code);
    void deleteStudent(Long id);
    Student updateStudent(Student student);
    Optional<Student> findStudentByEmail(String email);
    
}
