package pe.edu.utp.university_system.course;

import java.util.List;

public record CourseDetailsResponse(
        Long id,
        String name,
        String courseCode,
        Integer credits,
        List<ModuleResponse> modules
) {}
