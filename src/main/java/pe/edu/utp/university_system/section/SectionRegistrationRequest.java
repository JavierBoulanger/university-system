package pe.edu.utp.university_system.section;

import lombok.Data;

@Data
public class SectionRegistrationRequest {

    private String sectionName;

    private Long courseId;

    private Long teacherId;
}
