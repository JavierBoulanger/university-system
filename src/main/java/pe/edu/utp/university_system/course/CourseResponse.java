package pe.edu.utp.university_system.course;

public record CourseResponse(
        Long id,
        String name,
        String courseCode,
        Integer credits
){}
