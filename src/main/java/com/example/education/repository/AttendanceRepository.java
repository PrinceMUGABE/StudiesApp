package com.example.education.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.Attendance;
import com.example.education.modal.Student;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

    List<Attendance> findAttendancesByStudentAndDateAfter(Student student, LocalDate startDate);

    boolean existsByStudentAndDate(Student student, LocalDate date);

}
