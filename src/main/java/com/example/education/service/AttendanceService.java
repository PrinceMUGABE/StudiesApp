package com.example.education.service;

import com.example.education.modal.Attendance;
import com.example.education.modal.Student;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getAllAtendances();
    void checkAttendanceAndSendAlerts();
    Long countConsecutiveDaysMissed(Student student, LocalDate startDate);
    Attendance saveAttendance(Attendance attendance);

}
