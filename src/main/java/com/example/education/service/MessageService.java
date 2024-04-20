// package com.example.education.service;

// import org.aspectj.bridge.Message;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.education.modal.Student;

// @Service
// public class MessageService {
    
//     @Autowired
//     private EmailService emailService; // You'll need to implement this service for sending emails
    
//     @Autowired
//     private MessageRepository messageRepository; // Assuming you have a repository for messages
    
//     public void sendMessageToStudent(Student student, String messageContent) {
//         Message message = new Message();
//         message.setStudent(student);
//         message.setContent(messageContent);
//         messageRepository.save(message);
        
//         // Send email to student with the message content
//         emailService.sendEmail(student.getEmail(), "Daily Message", messageContent);
//     }
    
//     public void markMessageAsChecked(Message message) {
//         message.setChecked(true);
//         messageRepository.save(message);
//     }
// }
