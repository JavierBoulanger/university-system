package pe.edu.utp.university_system.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String teacherCode;

    private String email;

    private String specialty;

    private String academicDegree;

    private Boolean active;
}