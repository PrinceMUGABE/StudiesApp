package com.example.education.service;

import com.example.education.modal.Course;
import com.example.education.modal.Mark;
import java.util.List;
import java.util.Optional;

public interface MarkService {
    Mark saveMarks(Mark mark);
    void deleteMarks(Long id);
    Mark updateMarks(Mark mark);
    List<Mark> displayAllMarks();
    Optional<Mark> findMarksById(Long id);
    Mark findMarksByValue(int value);
    Mark findMarksByCourseCode(Course courseCode);
    Optional<Mark> findMarkByStudentAndCourse(Long studentId, Long courseId);

}
