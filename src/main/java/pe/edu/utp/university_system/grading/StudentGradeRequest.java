package pe.edu.utp.university_system.grading;

import lombok.Data;

@Data
public class StudentGradeRequest {

    private Long enrollmentId;

    private Double value;
}