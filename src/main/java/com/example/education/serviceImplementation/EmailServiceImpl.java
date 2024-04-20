package com.example.education.serviceImplementation;

import com.example.education.modal.User;
import com.example.education.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendAttendanceAlert(String email, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email); // Set recipient email address
            helper.setSubject("Attendance Alert"); // Set email subject
            helper.setText(message); // Set email message
            javaMailSender.send(mimeMessage); // Send the email
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle messaging exception
        }
    }
}
