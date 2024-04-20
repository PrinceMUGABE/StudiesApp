package com.example.education.serviceImplementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.education.modal.Attendance;
import com.example.education.modal.Student;
import com.example.education.modal.User;
import com.example.education.repository.AttendanceRepository;
import com.example.education.service.AttendanceService;

@Service
public class AttendanceServiceImplementation implements AttendanceService {

    @Autowired
    private AttendanceRepository repo;

    @Autowired
    private StudentServiceImplementation studentServiceImplementation;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Override
    public List<Attendance> getAllAtendances() {
        return repo.findAll();
    }

    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void checkAttendanceAndSendAlerts() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        List<Student> students = studentServiceImplementation.displayAllStudents();
        for (Student student : students) {
            long consecutiveDaysMissed = countConsecutiveDaysMissed(student, sevenDaysAgo);
            if (consecutiveDaysMissed >= 7) {
                // Send email alert to responsible officers
                Optional<User> user = userServiceImplementation.findUserByEmail(student.getEmail());
                String message;
                // emailService.sendAttendanceAlert(null, message);
            } else {
                return;
            }
        }
    }

    @Override
    public Long countConsecutiveDaysMissed(Student student, LocalDate startDate) {
        LocalDate currentDate = LocalDate.now();
        long consecutiveDaysMissed = 0;
        
        LocalDate dateToCheck = startDate;
        
        while (!dateToCheck.isAfter(currentDate)) {
            // Check if the student has attendance for the current date
            boolean hasAttendance = hasAttendanceForDate(student, dateToCheck);
            
            if (!hasAttendance) {
                consecutiveDaysMissed++;
            } else {
                // Reset consecutive days missed if attendance is found
                consecutiveDaysMissed = 0;
            }
            
            // Move to the next day
            dateToCheck = dateToCheck.plusDays(1);
        }
        
        return consecutiveDaysMissed;
    }


    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return repo.save(attendance);
    }

    // Define the hasAttendanceForDate method
    public boolean hasAttendanceForDate(Student student, LocalDate date) {
        return repo.existsByStudentAndDate(student, date);
    }
}
