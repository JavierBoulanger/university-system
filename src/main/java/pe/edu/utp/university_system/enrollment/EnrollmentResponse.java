package pe.edu.utp.university_system.enrollment;

public record EnrollmentResponse(
        Long id,
        Long studentId,
        Long sectionId,
        String academicTerm
) {}
