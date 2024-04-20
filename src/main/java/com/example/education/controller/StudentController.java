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
import com.example.education.modal.School;
import com.example.education.modal.Student;
import com.example.education.modal.User;
import com.example.education.serviceImplementation.CourseServiceImplementation;
import com.example.education.serviceImplementation.DepartmentServiceImplementation;
import com.example.education.serviceImplementation.SchoolServiceImplementation;
import com.example.education.serviceImplementation.StudentServiceImplementation;
import com.example.education.serviceImplementation.UserServiceImplementation;

import java.util.ArrayList;



@Controller
public class StudentController {
    @Autowired
    private StudentServiceImplementation serviceImplementation;
    @Autowired
    private SchoolServiceImplementation schoolServiceImplementation;
    @Autowired
    private CourseServiceImplementation courseServiceImplementation;
    @Autowired
    private UserServiceImplementation userServiceImplementation;
    @Autowired
    private DepartmentServiceImplementation departmentServiceImplementation;

    @GetMapping("/list-students")
    public String showAllStudents(Model model) {
        try {
            List<Student> students = serviceImplementation.displayAllStudents();
            model.addAttribute("students", students);
            return "manage_all_students";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching students");
            return "error";
        }
    }

    @GetMapping("/create-new-student-page") // Change the mapping URL
    public String showNewSchoolCreationPage(Model model) {
        List<School> schools = schoolServiceImplementation.displayAllSchools();
        List<Course> courses = courseServiceImplementation.displayAllCourses();
        model.addAttribute("schools", schools);
        model.addAttribute("courses", courses);
        return "add_new_student";
    }



    @PostMapping("/save_new_student")
public String saveNewStudent(Model model, @RequestParam String code, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, 
@RequestParam String phone, @RequestParam Long schoolId,@RequestParam Long departmentId, @RequestParam List<Long> courseIds){

    if (code.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || phone.isEmpty() 
    || schoolId == null || courseIds.isEmpty()) {
        model.addAttribute("Message", "Fill all the fields");
    } else {
        
        try {
            Student student = new Student();

            student.setStudentCode(code);
            student.setFirstname(firstname);
            student.setLastname(lastname);
            student.setEmail(email);
            student.setPhone(phone);

            // Set school

            Optional<School> savedSchool = schoolServiceImplementation.findSchoolById(schoolId);
            if (savedSchool.isPresent()) {
                
                // Set department
                Optional<Department> schoolDepartment = departmentServiceImplementation.findDepartmentBySchool(savedSchool);
                if (schoolDepartment.isPresent()) {
                    student.setDepartment(schoolDepartment.get());
                } else {
                    model.addAttribute("Message", "School not found");
                    return "add_new_student";
                }
            }
            


            // Set courses
            List<Course> courses = new ArrayList<>();
            for (Long courseId : courseIds) {
                Optional<Course> courseOptional = courseServiceImplementation.findCourseById(courseId);
                if (courseOptional.isPresent()) {
                    courses.add(courseOptional.get());
                } else {
                    model.addAttribute("Message", "Course with ID " + courseId + " not found");
                    return "add_new_student";
                }
            }
            student.setCourses(courses);

            // Save student
            serviceImplementation.saveStudent(student);
            model.addAttribute("Message", "Student saved successfully");
            return "redirect:/list-students";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("Message", "An error occurred while saving student: " + ex.getMessage());
            return "add_new_student";
        }
        
    }

    return "add_new_student";
}

    



    @PostMapping("/delete-student")
    public String deleteStudent(Model model, @RequestParam Long id) {
        Optional<Student> savedStudent = serviceImplementation.findStudentById(id);
        if (savedStudent.isPresent()) {
            serviceImplementation.deleteStudent(id);
            model.addAttribute("message", "School deleted successfully!");
        } else {
            model.addAttribute("error", "School not found with id: " + id);
        }
        // Redirect to display all schools after deletion
        return "redirect:/list-students";
    }


    @GetMapping("/edit-student/{id}")
    public String editStudent(Model model, @PathVariable Long id) {
        Optional<Student> savedStudent = serviceImplementation.findStudentById(id);
        if (savedStudent.isPresent()) {
            Student student = savedStudent.get();
            List<School> schools = schoolServiceImplementation.displayAllSchools();
            List<Course> courses = courseServiceImplementation.displayAllCourses();
            model.addAttribute("student", student);
            model.addAttribute("schools", schools);
            model.addAttribute("courses", courses);
            return "edit-student";
        } else {
            model.addAttribute("error", "Student not found with id: " + id);
            return "redirect:/list-students";
        }
    }


    @PostMapping("/update-student")
    public String updateStudent(Model model, @RequestParam("id") Long id, @RequestParam String code, @RequestParam String firstname, @RequestParam String lastname, @RequestParam("email") String email, 
    @RequestParam String phone, @RequestParam Long schoolId, @RequestParam List<Long> courseIds) {
        Optional<Student> savedStudent = serviceImplementation.findStudentById(id);
        if (savedStudent.isPresent()) {
            Student student = savedStudent.get();
            student.setFirstname(firstname);
            student.setLastname(lastname);
            student.setEmail(email);
            student.setPhone(phone);




            // Set school
            Optional<School> schoolOptional = schoolServiceImplementation.findSchoolById(schoolId);
            if (schoolOptional.isPresent()) {
                Optional<Department> foundDepartment = departmentServiceImplementation.findDepartmentBySchool(schoolOptional);
                if (foundDepartment.isPresent()) {
                    student.setDepartment(foundDepartment.get());

                    // Set courses
                    List<Course> courses = new ArrayList<>();
                    for (Long courseId : courseIds) {
                        Optional<Course> courseOptional = courseServiceImplementation.findCourseById(courseId);
                        if (courseOptional.isPresent()) {
                            courses.add(courseOptional.get());
                        } else {
                            model.addAttribute("Message", "Course with ID " + courseId + " not found");
                            return "add_new_student";
                        }
                    }
                    student.setCourses(courses);


                    serviceImplementation.updateStudent(student);
                    model.addAttribute("message", "Student updated successfully!");
                    return "redirect:/list-students";
                }else{
                    model.addAttribute("message", "Department not found");
                }
                
            } else {
                model.addAttribute("Message", "School not found");
                return "add_new_student";
            }

            
        } else {
            model.addAttribute("error", "Student not found with id: " + id);
        }
        return "redirect:/list-students";
    }



    @GetMapping("/student-update-profile/{id}")
    public String getStudentEditProfilePage(@PathVariable Long id, Model model) {
        Optional<Student> savedStudent = serviceImplementation.findStudentById(id);
        if (savedStudent.isPresent()) {
            Student student = savedStudent.get();
            List<School> schools = schoolServiceImplementation.displayAllSchools();
            List<Course> courses = courseServiceImplementation.displayAllCourses();
            model.addAttribute("student", student);
            model.addAttribute("schools", schools);
            model.addAttribute("courses", courses);
            return "student_edit_profile_page"; // Assuming the template name is "edit-student.html"
        } else {
            model.addAttribute("error", "Student not found with id: " + id);
            return "redirect:/list-students"; // Redirect to list of students if student not found
        }
    }
    
    @PostMapping("/student-update-profile/{id}")
    public String updateStudentProfile(Model model, @PathVariable Long id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, 
    @RequestParam String phone, @RequestParam Long schoolId, @RequestParam List<Long> courseIds) {
        Optional<Student> savedStudent = serviceImplementation.findStudentById(id);
        if (savedStudent.isPresent()) {
            Student student = savedStudent.get();
            student.setFirstname(firstname);
            student.setLastname(lastname);
            student.setEmail(email);
            student.setPhone(phone);
    
            // Set school and courses (similar to your existing logic)
    
            serviceImplementation.updateStudent(student);
            return "redirect:/student-dashboard?id=" + savedStudent.get().getId()+ "&email=" + email; // Redirect to dashboard with user ID
        } else {
            model.addAttribute("error", "Student not found with id: " + id);
            return "redirect:/student-dashboard?id=" + savedStudent.get().getId() + "&email=" + email; // Redirect to list of students if student not found
        }
    }
    

}
