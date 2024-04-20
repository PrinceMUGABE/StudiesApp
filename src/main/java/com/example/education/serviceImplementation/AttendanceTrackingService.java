package com.example.education.serviceImplementation;

import com.example.education.modal.Attendance;
import com.example.education.modal.Student;
import com.example.education.service.EmailService;
import com.example.education.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceTrackingService {

    @Autowired
    private StudentServiceImplementation studentService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void checkAttendanceAndSendAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        List<Student> students = studentService.displayAllStudents();
        for (Student student : students) {
            List<Attendance> attendances = student.getAttendances();
            long consecutiveDaysMissed = countConsecutiveDaysMissed(attendances, sevenDaysAgo);
            if (consecutiveDaysMissed >= 7) {
                // Send email alert to responsible officers
                // emailService.sendAttendanceAlert(student);
            }
        }
    }

    private long countConsecutiveDaysMissed(List<Attendance> attendances, LocalDate startDate) {
        long consecutiveDaysMissed = 0;
        LocalDate dateToCheck = startDate;
        for (Attendance attendance : attendances) {
            if (attendance.getDate().equals(dateToCheck)) {
                consecutiveDaysMissed = 0; // Reset counter
            } else {
                consecutiveDaysMissed++;
                if (consecutiveDaysMissed >= 7) {
                    return consecutiveDaysMissed; // Return early if 7 consecutive days missed
                }
            }
            dateToCheck = dateToCheck.plusDays(1);
        }
        return consecutiveDaysMissed;
    }
}
