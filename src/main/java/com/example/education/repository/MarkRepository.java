package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.education.modal.Course;
import com.example.education.modal.Mark;
import java.util.List;
import java.util.Optional;


public interface MarkRepository extends JpaRepository<Mark, Long>{

    Mark findMarksByValue(int value);
    
    @Query("SELECT m FROM Mark m JOIN FETCH m.student s JOIN FETCH m.course c")
    List<Mark> findAllWithDetails();

    Mark findMarksByCourse(Course courseCode);

    @Query("SELECT m FROM Mark m WHERE m.student.id = :studentId AND m.course.id = :courseId")
    Optional<Mark> findMarkByStudentAndCourse(Long studentId, Long courseId);

}
