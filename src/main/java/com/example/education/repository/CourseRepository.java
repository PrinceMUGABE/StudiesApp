package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findCourseByCourseName(String courseName);
    Course findCourseByCourseCode(String courseCode);
    // Course findStudentInCourse(String studentCode);
}
