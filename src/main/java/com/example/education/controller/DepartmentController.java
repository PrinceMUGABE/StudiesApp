package com.example.education.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.education.modal.Department;
import com.example.education.modal.School;
import com.example.education.serviceImplementation.DepartmentServiceImplementation;
import com.example.education.serviceImplementation.SchoolServiceImplementation;

@Controller
public class DepartmentController {
    @Autowired
    private SchoolServiceImplementation schoolServiceImplementation;
    @Autowired
    private DepartmentServiceImplementation departmentServiceImplementation;

    @GetMapping("/list_departments")
    public String showAllAvailabeDepartments(Model model) {
        List<Department> departments = departmentServiceImplementation.getAllDepartments();
        model.addAttribute("departments", departments);

        return "manage_all_departments";

    }

    @GetMapping("/create-new-student")
    public String showNewStudentCreationPage(Model model) {
        List<School> schools = schoolServiceImplementation.displayAllSchools();
        model.addAttribute("schools", schools);
        return "add_new_student";
    }

    @GetMapping("/departments/{schoolId}")
    @ResponseBody
    public List<Department> getDepartmentsBySchool(@PathVariable Long schoolId) {
        Optional<School> school = schoolServiceImplementation.findSchoolById(schoolId);
        if (school.isPresent()) {
            return departmentServiceImplementation.findDepartmentsBySchool(school.get());
        } else {
            return new ArrayList<>(); // Return an empty list
        }
    }


    @PostMapping("/save_new_department")
    public String saveNewDepartment(Model modal, @RequestParam String name, @RequestParam Long school, @RequestParam Long department, @RequestParam Long total) {
        
            Optional<School> foundSchool = schoolServiceImplementation.findSchoolById(school);
            if (foundSchool.isPresent()) {
                Department newDepartment = new Department();
                newDepartment.setName(name);
                newDepartment.setSchool(foundSchool.get());
                newDepartment.setTotalStudent(total);

                departmentServiceImplementation.saveDepartment(newDepartment);
                modal.addAttribute("message", "Department saved successfully");
                return "redirect:/list_departments";
            }else{
                modal.addAttribute("error", "School not found");
                return "redirect:/list_departments";
            }


    }

    @PostMapping("/delete-department/{id}")
    public String deleteDepartment(Model model, @PathVariable Long id) {
        try {
            departmentServiceImplementation.deleteDepartmentById(id);
            model.addAttribute("message", "Department deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete Department with id: " + id);
        }
        return "redirect:/list_departments" ;
    }

    @GetMapping("/edit-department/{id}")
    public String editDepartment(Model model, @PathVariable Long id) {
        List<School> schools = schoolServiceImplementation.displayAllSchools();
        model.addAttribute("schools", schools);

        // Fetch the department by its ID
        Optional<Department> departmentOptional = departmentServiceImplementation.findDepartmentById(id);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            model.addAttribute("department", department); // Add the department object to the model
            return "edit-department"; // Render the edit-department template
        } else {
            // Handle case where department with the given ID is not found
            model.addAttribute("error", "Department not found");
            return "redirect:/list_departments"; // Redirect to department list page
        }
    }



    @PostMapping("/update-department")
    public String updateDepartment(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam Long school, @RequestParam Long total) {

        Optional<Department> savedDepartment = departmentServiceImplementation.findDepartmentById(id);
        if (savedDepartment.isPresent()) {
            
            Optional<School> foundSchool = schoolServiceImplementation.findSchoolById(school);
            if (foundSchool.isPresent()) {
                Department newDepartment = savedDepartment.get();
                newDepartment.setName(name);
                newDepartment.setSchool(foundSchool.get());
                newDepartment.setTotalStudent(total);

                departmentServiceImplementation.updateDepartment(newDepartment);
                model.addAttribute("message", "Department Updated successfully");
                return "redirect:/list_departments";
            }else{
                model.addAttribute("error", "School not found");
                return "redirect:/list_departments";
            }

        } else {
            model.addAttribute("success", "Department not found");
            return "redirect:/list_departments";
        }
    }
}


