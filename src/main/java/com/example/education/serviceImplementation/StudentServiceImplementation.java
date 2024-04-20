package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Student;
import com.example.education.modal.User;
import com.example.education.repository.StudentRepository;
import com.example.education.service.StudentService;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImplementation implements StudentService{

    @Autowired
    private StudentRepository repo;

    @Transactional
    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public List<Student> displayAllStudents() {
        return repo.findAll();
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Student findStudentByCode(String code) {
        return repo.findStudentByStudentCode(code);
    }

    @Override
    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Student updateStudent(Student student) {
       Optional<Student> savedStudent = repo.findById(student.getId());
       if (savedStudent != null) {
            Student updaStudent =  savedStudent.get();
            updaStudent.setStudentCode(student.getStudentCode());
            updaStudent.setFirstname(student.getFirstname());
            updaStudent.setLastname(student.getLastname());
            updaStudent.setEmail(student.getEmail());
            updaStudent.setPhone(student.getPhone());
            updaStudent.setDepartment(student.getDepartment());
            updaStudent.setCourses(student.getCourses());

            return repo.save(updaStudent);
       }else{
        throw new RuntimeException("User not found with id: " + student.getId());
       }
    }

    @Override
    public Optional<Student> findStudentByEmail(String email) {
        return repo.findStudentByEmail(email);
    }
}
