package com.example.education.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.education.modal.Course;
import com.example.education.modal.Mark;
import com.example.education.modal.Student;
import com.example.education.serviceImplementation.CourseServiceImplementation;
import com.example.education.serviceImplementation.MarkServiceImplementation;
import com.example.education.serviceImplementation.StudentServiceImplementation;

@Controller
public class TeacherController {
    @Autowired
    private MarkServiceImplementation serviceImplementation;
    @Autowired
    private StudentServiceImplementation studentServiceImplementation;
    @Autowired
    private CourseServiceImplementation courseServiceImplementation;
    

    @GetMapping("/teacher/list-marks")
    public String showAllAvailableMarks(Model model) {
        List<Mark> marks = serviceImplementation.displayAllMarks();
        model.addAttribute("marks", marks);
        return "teacher-dashboard";
        
    }


    @GetMapping("/insert-marks")
    public String showNewMarkCreationPage(Model model) {
        List<Student> students = studentServiceImplementation.displayAllStudents();
        List<Course> courses = courseServiceImplementation.displayAllCourses();
        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        return "add_new_marks";
    }

    @PostMapping("/save_mark")
    public String saveNewMarks(Model model, @RequestParam int value, @RequestParam Long studentId, @RequestParam Long courseId) {
        if (studentId == null || courseId == null) {
            model.addAttribute("error", "Please select both student and course");
            return "add_new_marks";
        } else {
            
            Student savedStudent = studentServiceImplementation.findStudentById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
            Course selectedCourse = courseServiceImplementation.findCourseById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
       
            Mark mark = new Mark();
            mark.setStudent(savedStudent);
            mark.setCourse(selectedCourse);
            mark.setValue(value);

            serviceImplementation.saveMarks(mark); // Save the mark
            model.addAttribute("message", "Marks saved successfully");
            return "redirect:/teacher/list-marks"; // Redirect to the manage marks page
        }
    }


    @PostMapping("/remove-marks")
    public String deleteMarks(Model model, @RequestParam Long id) {
        Optional<Mark> savedMarks = serviceImplementation.findMarksById(id);
        if (savedMarks.isPresent()) {
            serviceImplementation.deleteMarks(id);
            model.addAttribute("message", "Marks deleted successfully!");
            return "redirect:/teacher/list-marks";
        } else {
            model.addAttribute("error", "Marks not found with id: " + id);
            return "redirect:/teacher/list-marks";
        }
    }

    @GetMapping("/update-mark/{id}")
    public String editMarks(Model model, @PathVariable Long id) {
        Optional<Mark> savedMarks = serviceImplementation.findMarksById(id);
        if (savedMarks.isPresent()) {
            model.addAttribute("mark", savedMarks.get());
            List<Student> students = studentServiceImplementation.displayAllStudents();
            List<Course> courses = courseServiceImplementation.displayAllCourses();
            model.addAttribute("students", students);
            model.addAttribute("courses", courses);
            return "edit-marks";
        } else {
            model.addAttribute("error", "Marks not found with id: " + id);
            return "redirect:/teacher/list-marks";
        }
    }

    @PostMapping("/update-mark")
    public String updateMarks(Model model, @RequestParam Long id, @RequestParam int value, @RequestParam Long studentId, @RequestParam Long courseId) {
        Optional<Mark> markOptional = serviceImplementation.findMarksById(id);
        if (markOptional.isPresent()) {
            Mark mark = markOptional.get();
            mark.setValue(value);
            serviceImplementation.updateMarks(mark);
            model.addAttribute("message", "Marks updated successfully");
            return "redirect:/teacher/list-marks";
        } else {
            model.addAttribute("error", "Failed to update marks, mark not found");
            return "redirect:/teacher/list-marks";
        }
    }
}
