package pe.edu.utp.university_system.grading;

import lombok.Data;

import java.util.List;

@Data
public class BulkGradeRegistrationRequest {

    private Long evaluationId;

    private List<StudentGradeRequest> grades;
}
