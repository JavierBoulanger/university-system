package pe.edu.utp.university_system.enrollment;

import lombok.Data;

@Data
public class EnrollmentRegistrationRequest {

    private Long studentId;

    private Long sectionId;

    private String academicTerm;
}