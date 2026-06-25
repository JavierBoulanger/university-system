package pe.edu.utp.university_system.teacher;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherRegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String teacherCode;

    private String specialty;

    private String phone;

    private String documentNumber;

    private LocalDate hireDate;

    private String academicDegree;
}