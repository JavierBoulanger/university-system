package pe.edu.utp.university_system.course;

import lombok.Data;
import java.util.List;

@Data
public class ModuleRegistrationRequest {

    private Integer moduleNumber;

    private List<String> topics;

    private List<EvaluationRegistrationRequest> evaluations;

}
