package pe.edu.utp.university_system.section;

public record SectionResponse(
        Long id,
        String sectionName,
        Long courseId,
        Long teacherId
) {}
