package com.example.education.service;

import com.example.education.modal.Student;
import com.example.education.modal.User;

public interface EmailService {
    void sendAttendanceAlert(String email, String message);
}
