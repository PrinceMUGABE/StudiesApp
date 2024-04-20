package com.example.education.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;


import com.example.education.modal.Attendance;
// import com.example.education.modal.Student;
// import com.example.education.modal.User;
import com.example.education.serviceImplementation.AttendanceServiceImplementation;
import com.example.education.serviceImplementation.EmailServiceImpl;
import com.example.education.serviceImplementation.StudentServiceImplementation;
import com.example.education.serviceImplementation.UserServiceImplementation;

@Controller
public class AttendanceController {
    @Autowired
    private AttendanceServiceImplementation attendanceServiceImplementation;
    @Autowired
    private UserServiceImplementation userServiceImplementation;
    @Autowired
    private StudentServiceImplementation studentServiceImplementation;
    @Autowired
    private EmailServiceImpl emailServiceImpl;


    @GetMapping("/list_attendances")
    public String showAllStudentAttendances(Model model) {
        List<Attendance> attendances = attendanceServiceImplementation.getAllAtendances();
        model.addAttribute("attendances", attendances);

        return "manage_all_attendances";

    }   
    
}


