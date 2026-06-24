package pe.edu.utp.university_system.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.course.Course;
import pe.edu.utp.university_system.course.Topic;
import pe.edu.utp.university_system.enrollment.Enrollment;
import pe.edu.utp.university_system.course.Evaluation;
import pe.edu.utp.university_system.course.EvaluationType;
import pe.edu.utp.university_system.course.Module;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIRecommendationService {

    private final AIRecommendationRepository repository;

    @Value("${gemini.model}")
    private String modelName;

    public void generateRecommendation(
            Enrollment enrollment,
            Evaluation evaluation,
            Double gradeValue) {

        if (evaluation.getEvaluationType()
                == EvaluationType.FINAL_EXAM) {

            return;
        }

        List<String> topics =
                getTopicsForReinforcement(evaluation);

        String prompt =
                buildPrompt(
                        enrollment,
                        evaluation,
                        topics,
                        gradeValue
                );

        String aiResponse =
                """
                        PRueba
                        """;

        AIRecommendation recommendation = new AIRecommendation();

        recommendation.setEnrollment(enrollment);
        recommendation.setGradeObtained(gradeValue);
        recommendation.setEvaluationTitle(evaluation.getTitle());
        recommendation.setModelName(modelName);
        recommendation.setPrompt(prompt);
        recommendation.setRecommendationContent(aiResponse);
        recommendation.setStatus(RecommendationStatus.GENERATED);
        recommendation.setGeneratedAt(LocalDateTime.now());
        repository.save(recommendation);
    }

    private String buildPrompt(
            Enrollment enrollment,
            Evaluation evaluation,
            List<String> topics,
            Double gradeValue) {

        String topicsText =
                String.join(", ", topics);

        return """
                Eres un profesor universitario experimentado.
                
                El estudiante obtuvo %.2f en %s.
                
                Explica los siguientes temas:
                
                %s
                
                Luego proporciona:
                
                1. Una explicación concisa.
                2. Ejemplos prácticos.
                3. Dos ejercicios.
                4. Recomendaciones de estudio.
                
                Mantén un tono motivador.
                """
                .formatted(
                        gradeValue,
                        evaluation.getTitle(),
                        topicsText
                );
    }

    private List<String> getTopicsForReinforcement(Evaluation evaluation) {
        List<String> topics = new ArrayList<>();

        int currentModule = evaluation.getModule().getModuleNumber();
        Course course = evaluation.getModule().getCourse();

        int previousEvalModule = 0;

        // Encontrar dinámicamente en qué módulo fue la evaluación anterior
        for (Module module : course.getModules()) {
            if (module.getModuleNumber() < currentModule && !module.getEvaluations().isEmpty()) {
                if (module.getModuleNumber() > previousEvalModule) {
                    previousEvalModule = module.getModuleNumber();
                }
            }
        }

        // Recolecta los temas estrictamente en ese rango
        for (Module module : course.getModules()) {
            if (module.getModuleNumber() > previousEvalModule && module.getModuleNumber() <= currentModule) {
                for (Topic topic : module.getTopics()) {
                    topics.add(topic.getName());
                }
            }
        }

        return topics;
    }
}