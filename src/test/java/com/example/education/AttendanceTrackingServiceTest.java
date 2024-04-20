// package com.example.education;

// import com.example.education.modal.Attendance;
// import com.example.education.modal.Student;
// import com.example.education.serviceImplementation.AttendanceTrackingService;
// // import com.example.education.serviceImplementation.EmailService;
// import com.example.education.serviceImplementation.EmailServiceImpl;
// import com.example.education.serviceImplementation.StudentServiceImplementation;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import static org.mockito.Mockito.*;

// public class AttendanceTrackingServiceTest {

//     @Mock
//     private StudentServiceImplementation studentService;

//     @Mock
//     private EmailServiceImpl emailService;

//     @InjectMocks
//     private AttendanceTrackingService attendanceTrackingService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testCheckAttendanceAndSendAlerts() {
//         // Mock data
//         LocalDate today = LocalDate.now();
//         LocalDate sevenDaysAgo = today.minusDays(7);
//         List<Attendance> attendances = new ArrayList<>();
//         for (int i = 0; i < 6; i++) {
//             attendances.add(new Attendance()); // Assuming a no-argument constructor exists
//         }

//         Student student = new Student();
//         student.setEmail("princemugabe568@gmail.com");
//         student.setAttendances(attendances);

//         List<Student> students = new ArrayList<>();
//         students.add(student);

//         // Mock service behavior
//         when(studentService.displayAllStudents()).thenReturn(students);
//         doNothing().when(emailService).sendAttendanceAlert(any(Student.class));

//         // Execute the method
//         attendanceTrackingService.checkAttendanceAndSendAlerts();

//         // Verify that the emailService was called once
//         verify(emailService, times(1)).sendAttendanceAlert(any(Student.class)); 
//     }
// }
