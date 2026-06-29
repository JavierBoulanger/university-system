package pe.edu.utp.university_system.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai-recommendations")
@RequiredArgsConstructor
public class AIRecommendationController {

    private final AIRecommendationRepository repository;

    @GetMapping("/student/{accountId}")
    public ResponseEntity<List<AIRecommendationResponse>> getRecommendationsForStudent(
            @PathVariable Long accountId,
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        if (!"STUDENT".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        List<AIRecommendation> recommendations = repository.findByStudentAccountId(accountId);

        List<AIRecommendationResponse> dtoList = recommendations.stream().map(rec -> {
            AIRecommendationResponse recommendation = new AIRecommendationResponse();
            recommendation.setId(rec.getId());
            recommendation.setCourseName(rec.getEnrollment().getSection().getCourse().getName());
            recommendation.setEvaluationTitle(rec.getEvaluationTitle());
            recommendation.setGradeObtained(rec.getGradeObtained());
            recommendation.setRecommendationContent(rec.getRecommendationContent());
            recommendation.setGeneratedAt(rec.getGeneratedAt());
            return recommendation;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping(
            "/student/{accountId}/unread-count"
    )
    public ResponseEntity<Long>
    getUnreadCount(
            @PathVariable Long accountId,
            @RequestHeader(
                    value = "X-User-Role",
                    required = false
            )
            String role) {

        if (!"STUDENT".equals(role)) {
            return ResponseEntity
                    .status(403)
                    .build();
        }

        Long count =
                repository
                        .countUnreadRecommendations(
                                accountId
                        );

        return ResponseEntity.ok(
                count
        );
    }

    @PatchMapping(
            "/student/{accountId}/read-all"
    )
    public ResponseEntity<Void>
    markAllAsRead(
            @PathVariable Long accountId,
            @RequestHeader(
                    value = "X-User-Role",
                    required = false
            )
            String role) {

        if (!"STUDENT".equals(role)) {
            return ResponseEntity
                    .status(403)
                    .build();
        }

        repository.markAllAsRead(
                accountId
        );

        return ResponseEntity.ok()
                .build();
    }
}