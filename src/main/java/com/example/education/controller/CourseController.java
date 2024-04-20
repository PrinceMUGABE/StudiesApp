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
import com.example.education.modal.Department;
import com.example.education.serviceImplementation.CourseServiceImplementation;
import com.example.education.serviceImplementation.DepartmentServiceImplementation;

@Controller
public class CourseController {

    @Autowired
    private CourseServiceImplementation serviceImplementation;
    @Autowired
    private DepartmentServiceImplementation departmentServiceImplementation;

    @GetMapping("/list_course")
    public String showAllAvailableCourses(Model model) {
        List<Course> courses = serviceImplementation.displayAllCourses();
        model.addAttribute("courses", courses);
        return "manage_all_courses";
    }

    @GetMapping("/create-new-course")
    public String showNewCourseCreationPage(Model model) {
        List<Department> departments = departmentServiceImplementation.getAllDepartments();
        model.addAttribute("departments", departments);
        return "add_new_course";
    }

    @PostMapping("/save_new_course")
    public String saveNewCourse(Model model, @RequestParam String code, @RequestParam String name, @RequestParam Long department) {
        if (code.isEmpty() || name.isEmpty()) {
            model.addAttribute("message", "Fill all the fields");
            return "add_new_course";
        } else {
            try {
                Optional<Department> foundDepartment = departmentServiceImplementation.findDepartmentById(department);
                if (foundDepartment.isPresent()) {
                    Course course = new Course();
                    course.setCourseCode(code);
                    course.setCourseName(name);
                    course.setDepartment(foundDepartment.get());
                    Course savedCourse = serviceImplementation.saveCourse(course);
                    if (savedCourse != null) {
                        model.addAttribute("message", "Course " + savedCourse + " saved successfully");
                        return "redirect:/list_course";
                    } else {
                        model.addAttribute("message", "Failed to register new Course");
                        return "redirect:/list_course";
                    }
                    }
                else{
                    model.addAttribute("message", "Department not found");
                        return "redirect:/list_course";
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                return "add_new_course";
            }
        }
    }

    @PostMapping("/delete-course")
    public String deleteCourse(Model model, @RequestParam Long id) {
        Optional<Course> savedCourse = serviceImplementation.findCourseById(id);
        if (savedCourse.isPresent()) {
            serviceImplementation.deleteCourse(id);
            model.addAttribute("message", "Course deleted successfully!");
        } else {
            model.addAttribute("error", "Course not found with id: " + id);
        }
        return "redirect:/list_course";
    }


    @GetMapping("/edit-course/{id}")
    public String editCourse(Model model, @PathVariable Long id) {
        Optional<Course> savedCourse = serviceImplementation.findCourseById(id);
        List<Department> departments = departmentServiceImplementation.getAllDepartments();
        if (savedCourse.isPresent()) {
            Course course = savedCourse.get();
            model.addAttribute("course", course);
            model.addAttribute("departments", departments);
            return "edit-course";
        } else {
            model.addAttribute("error", "Course not found with id: " + id);
            model.addAttribute("departments", departments);
            return "redirect:/list_course";
        }
    }

    @PostMapping("/update-course")
    public String updateCourse(Model model, @RequestParam Long id, @RequestParam String name,
            @RequestParam String code,
            @RequestParam Long department) {
        Optional<Department> foundDepartment = departmentServiceImplementation.findDepartmentById(department);
        if (foundDepartment.isPresent()) {
            Optional<Course> savedCourse = serviceImplementation.findCourseById(id);
            if (savedCourse.isPresent()) {
                Course course = savedCourse.get();
                course.setCourseCode(code);
                course.setCourseName(name);
                course.setDepartment(foundDepartment.get());
                serviceImplementation.updatCourse(course);
                model.addAttribute("message", "Course updated successfully!");
            } else {
                model.addAttribute("error", "Course not found with id: " + id);
            }
            return "redirect:/list_course";
            }
        else{
            model.addAttribute("message", "Department not found");
            return "redirect:/list_course";
        }
        
    }
}
