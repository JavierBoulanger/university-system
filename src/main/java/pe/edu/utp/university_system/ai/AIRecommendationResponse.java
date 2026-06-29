package pe.edu.utp.university_system.ai;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AIRecommendationResponse {

    private Long id;

    private String courseName;

    private String evaluationTitle;

    private Double gradeObtained;

    private String recommendationContent;

    private Boolean readByStudent;

    private LocalDateTime generatedAt;
}
