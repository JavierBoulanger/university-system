package pe.edu.utp.university_system.grading;

public record GradeResponse(
        Long id,
        Long enrollmentId,
        Long evaluationId,
        Double value
) {}