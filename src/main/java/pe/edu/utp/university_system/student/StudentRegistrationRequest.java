package pe.edu.utp.university_system.student;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String enrollmentCode;

    private String phone;

    private String documentNumber;

    private LocalDate birthDate;
}