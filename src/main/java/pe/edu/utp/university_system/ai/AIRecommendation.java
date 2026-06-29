package pe.edu.utp.university_system.ai;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.university_system.enrollment.Enrollment;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_recommendation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "enrollment_id",
            nullable = false
    )
    private Enrollment enrollment;

    @Column(nullable = false)
    private Double gradeObtained;

    @Column(nullable = false, length = 100)
    private String evaluationTitle;

    @Column(nullable = false, length = 50)
    private String modelName;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String recommendationContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationStatus status;

    @Column(nullable = false)
    private Boolean readByStudent = false;

    @Column(nullable = false)
    private LocalDateTime generatedAt;
}