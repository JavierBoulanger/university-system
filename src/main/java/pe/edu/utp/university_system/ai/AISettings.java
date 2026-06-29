package pe.edu.utp.university_system.ai;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AISettings {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false, length = 50)
    private String provider = "GEMINI";

    @Column(nullable = false, length = 50)
    private String modelName = "gemini-1.5-pro";

    @Column(nullable = false)
    private Double temperature = 0.7;

    @Column(nullable = false)
    private Double minimumGradeThreshold = 12.0;

    @Column(nullable = false)
    private Integer maxOutputTokens = 2048;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String systemPrompt;
}