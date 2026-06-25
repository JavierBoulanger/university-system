package pe.edu.utp.university_system.student;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String enrollmentCode;

    private String email;

    private Boolean active;
}