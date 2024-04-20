package com.example.education.controller;

import com.example.education.enumeration.UserRole;
import com.example.education.modal.Attendance;
import com.example.education.modal.Course;
import com.example.education.modal.School;
import com.example.education.modal.Student;
import com.example.education.modal.User;
import com.example.education.serviceImplementation.AttendanceServiceImplementation;
import com.example.education.serviceImplementation.CourseServiceImplementation;
import com.example.education.serviceImplementation.EmailServiceImpl;
import com.example.education.serviceImplementation.SchoolServiceImplementation;
import com.example.education.serviceImplementation.StudentServiceImplementation;
import com.example.education.serviceImplementation.UserServiceImplementation;

import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    private AttendanceServiceImplementation attendanceServiceImplementation;
    @Autowired
    private StudentServiceImplementation studentServiceImplementation;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private SchoolServiceImplementation schoolServiceImplementation;
    @Autowired
    private CourseServiceImplementation courseServiceImplementation;

    @GetMapping("/")
    public String startApp() {
        return "index";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
       
        newUser.setRole(UserRole.STUDENT); // Defaulting to STUDENT role for now
        userService.saveUser(newUser);
        model.addAttribute("message", "User signed up successfully!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("message", "User logged in successfully!");
            // Logic to redirect users based on their roles
            if (user.getRole() == UserRole.STUDENT) {
                // Redirect to the student dashboard with appropriate parameters
                return "redirect:/student-dashboard?id=" + user.getId() + "&email=" + user.getEmail();
            } else if (user.getRole() == UserRole.TEACHER) {
                return "redirect:/teacher/list-marks";
            } else if (user.getRole() == UserRole.HEAD_TEACHER) {
                return "redirect:/head-teacher-dashboard";
            } else if (user.getRole() == UserRole.EDUCATION_OFFICER) {
                return "redirect:/education-officer-dashboard";
            }
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }



    @GetMapping("/education-officer-dashboard")
    public String showEducationOfficerDashboard(Model model) {
        // Here you can fetch all users from the database and add them to the model
        // For demonstration, let's assume we have a list of users already
        List<User> users = userService.displayAllUsers();
        model.addAttribute("users", users);
        return "education_officer_dashboard";
    }






    @GetMapping("/manage-users")
    public String showManageUsersPage(Model model) {
        List<User> users = userService.displayAllUsers();
        model.addAttribute("users", users);
        return "manage_users";
    }

    @GetMapping("/edit-user")
    public String showEditUserPage(@RequestParam Long id, Model model) {
        Optional<User> optionalUser = userService.findUserById(id);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        return "edit_user";
    }


    @PostMapping("/update-user")
    public String updateUser(@RequestParam Long id, @RequestParam String username, @RequestParam String email, 
            @RequestParam UserRole role, Model model) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setId(id);
            updatedUser.setUsername(username);
            updatedUser.setEmail(email);
            updatedUser.setRole(role);
            userService.updateUser(updatedUser);
            model.addAttribute("message", "User updated successfully!");
            return "redirect:/manage-users";
        }else{
            model.addAttribute("message", "Failed to update a user");
            return "redirect:/manage-users";
        }
        
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("message", "User deleted successfully!");
        return "redirect:/manage-users";
    }

    @GetMapping("/assign-role")
    public String showAssignRolePage(@RequestParam Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "assign_role";
    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam Long id, @RequestParam UserRole role, Model model) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setRole(role);
            userService.updateUser(foundUser);
            model.addAttribute("message", "Role assigned successfully!");
            return "redirect:/manage-users";
        }else{
            model.addAttribute("message", "Failed to assign user role");
            return "redirect:/manage-users";
        }
        
    }


    @GetMapping("/student-dashboard")
    public String showStudentDashboard(Model model, @RequestParam("id") Long userId, @RequestParam("email") String email) {
        // Check if the student has accessed the dashboard for the current day
        LocalDate currentDate = LocalDate.now();
        Optional<User> foundOptionalUser = userService.findUserById(userId);
        if (foundOptionalUser.isPresent()) {
            Optional<Student> foundStudent = studentServiceImplementation.findStudentByEmail(email);
            if (foundStudent.isPresent()) {
                Student student = foundStudent.get();
                
                // Send email notification for successful login
                sendLoginSuccessEmail(email);

                if (!hasAttendanceForToday(student)) {
                    // If the student has not accessed the dashboard for the current day, save a missed attendance
                    Attendance attendance = new Attendance();
                    attendance.setStudent(student);
                    attendance.setDate(currentDate);
                    attendanceServiceImplementation.saveAttendance(attendance);
                }

                model.addAttribute("id", userId);
                return "student-dashboard";
            } else {
                model.addAttribute("error", "Student not found");
                return "redirect:/login";
            }
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/login";
        }
    }


    // Method to send email notification for successful login
    private void sendLoginSuccessEmail(String email) {
        // Implement email sending logic here
        String message = "You have logged in successfully.";
        emailServiceImpl.sendAttendanceAlert(email, message);
    }

    // Scheduled task to check and send email notifications for missed attendance
    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void checkAndSendAttendanceAlerts() {
        LocalDate currentDate = LocalDate.now();
        List<Student> students = studentServiceImplementation.displayAllStudents();
        for (Student student : students) {
            if (!hasAttendanceForToday(student)) {
                // If the student has missed attendance for the current day, send an email notification
                sendAttendanceAlert(student.getEmail());
            } else {
                // Send attendance success email notification
                sendAttendanceSuccessEmail(student.getEmail());
            }
        }
    }

    // Method to send email notification for missed attendance
    private void sendAttendanceAlert(String email) {
        // Implement email sending logic here
        String message = "You have missed attendance for today.";
        emailServiceImpl.sendAttendanceAlert(email, message);
    }

    // Method to send email notification for successful attendance
    private void sendAttendanceSuccessEmail(String email) {
        // Implement email sending logic here
        String message = "You have marked attendance for today successfully.";
        emailServiceImpl.sendAttendanceAlert(email, message);
    }

    // Method to check if the student has accessed the dashboard for the current day
    private boolean hasAttendanceForToday(Student student) {
        LocalDate currentDate = LocalDate.now();
        return attendanceServiceImplementation.hasAttendanceForDate(student, currentDate);
    }

    // Method to send email notification for 7 consecutive days of missed attendance to responsible officers
    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void sendAttendanceAlertForSevenConsecutiveDaysMissed() {
        List<Student> students = studentServiceImplementation.displayAllStudents();
        for (Student student : students) {
            Long consecutiveDaysMissed = attendanceServiceImplementation.countConsecutiveDaysMissed(student, LocalDate.now().minusDays(7));
            if (consecutiveDaysMissed >= 7) {
                // Send email alert to responsible officers
                sendAlertToResponsibleOfficers(student);
            }
        }
    }

    // Method to send email alert to responsible officers for students with 7 consecutive days of missed attendance
    private void sendAlertToResponsibleOfficers(Student student) {
        // Implement logic to find responsible officers and send email alert
        // For demonstration purposes, let's assume we have a predefined list of email addresses for responsible officers
        List<String> responsibleOfficersEmails = Arrays.asList("headteacher@example.com", "educationofficer@example.com");
        for (String email : responsibleOfficersEmails) {
            // Generate email message listing the student with 7 consecutive days of missed attendance
            String message = "Student with ID " + student.getId() + " has missed attendance for 7 consecutive days.";
            emailServiceImpl.sendAttendanceAlert(email, message);
        }
    }



}
