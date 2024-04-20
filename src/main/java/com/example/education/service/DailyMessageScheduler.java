// package com.example.education.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import com.example.education.modal.Student;
// import com.example.education.serviceImplementation.StudentServiceImplementation;

// import java.util.List;

// @Component
// public class DailyMessageScheduler {

//     @Autowired
//     private MessageService messageService;

//     @Autowired
//     private StudentServiceImplementation studentServiceImplementation;

//     @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
//     public void generateDailyMessages() {
//         List<Student> students = studentServiceImplementation.displayAllStudents();
//         for (Student student : students) {
//             // Generate a unique message with a link for each student
//             String message = "Please mark your attendance for today: <a href='/mark-attendance/" + student.getId() + "'>Mark Attendance</a>";
//             messageService.sendMessageToStudent(student, message);
//         }
//     }
// }
