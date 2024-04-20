package com.example.education.serviceImplementation;

import java.util.* ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Course;
import com.example.education.modal.Mark;
import com.example.education.repository.MarkRepository;
import com.example.education.service.MarkService;

@Service
public class MarkServiceImplementation implements MarkService{

    @Autowired
    private MarkRepository repo;

    @Override
    public Mark saveMarks(Mark mark) {
       return repo.save(mark);
    }

    @Override
    public void deleteMarks(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Mark updateMarks(Mark mark) {
        Optional<Mark> savedMark = repo.findById(mark.getId());
        if (savedMark != null) {
             Mark updateMark =  new Mark();
             updateMark.setStudent(mark.getStudent());
             updateMark.setTeacher(mark.getTeacher());
             updateMark.setValue(mark.getValue());
             
 
             return repo.save(updateMark);
        }else{
         throw new RuntimeException("Mark not found with id: " + mark.getId());
        }
    }

    @Override
    public List<Mark> displayAllMarks() {
        return repo.findAllWithDetails();
    }


    @Override
    public Optional<Mark> findMarksById(Long id) {
        return repo.findById(id);
    }



    @Override
    public Mark findMarksByValue(int value) {
        return repo.findMarksByValue(value);
    }

    @Override
    public Mark findMarksByCourseCode(Course courseCode) {
        return repo.findMarksByCourse(courseCode);
    }

    @Override
    public Optional<Mark> findMarkByStudentAndCourse(Long studentId, Long courseId) {
        return repo.findMarkByStudentAndCourse(studentId, courseId);
    }

}
