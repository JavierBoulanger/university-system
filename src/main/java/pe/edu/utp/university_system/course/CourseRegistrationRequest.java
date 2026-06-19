package pe.edu.utp.university_system.course;

import lombok.Data;
import java.util.List;

@Data
public class CourseRegistrationRequest {

    private String name;

    private String courseCode;
    
    private Integer credits;

    private List<ModuleRegistrationRequest> modules;
}