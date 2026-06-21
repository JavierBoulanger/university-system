package pe.edu.utp.university_system.course;

import java.util.List;

public record ModuleResponse(
        Long id,
        Integer moduleNumber,
        List<TopicResponse> topics,
        List<EvaluationResponse> evaluations
) {}