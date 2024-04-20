package com.example.education.service;

import com.example.education.modal.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course saveCourse(Course course);
    List<Course> displayAllCourses();
    Optional<Course> findCourseById(Long id);
    Course findCourseByCourseCode(String code);
    Course findCourseByName(String name);
    void deleteCourse(Long id);
    Course updatCourse(Course  course);
    // Course findStudentInCourse(String studentCode);

}
