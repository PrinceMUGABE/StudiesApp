package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Course;
import com.example.education.repository.CourseRepository;
import com.example.education.service.CourseService;
@Service
public class CourseServiceImplementation implements CourseService{

    @Autowired
    private CourseRepository repo;


    @Override
    public Course saveCourse(Course course) {
        return repo.save(course);
    }

    @Override
    public List<Course> displayAllCourses() {
        return repo.findAll();
    }

    @Override
    public Optional<Course> findCourseById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Course findCourseByCourseCode(String code) {
        return repo.findCourseByCourseCode(code);
    }

    @Override
    public Course findCourseByName(String name) {
        return repo.findCourseByCourseName(name);
    }

    @Override
    public void deleteCourse(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Course updatCourse(Course course) {
        Optional<Course> savedCourse = repo.findById(course.getId());
        if (savedCourse.isPresent()) {
            Course updatedCourse = savedCourse.get();
            updatedCourse.setCourseCode(course.getCourseCode());
            updatedCourse.setCourseName(course.getCourseName());
            updatedCourse.setMarks(course.getMarks()); // Ensure this line doesn't create shared references
            updatedCourse.setStudents(course.getStudents());
            updatedCourse.setDepartment(course.getDepartment());
            return repo.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + course.getId());
        }
    }


 
}
