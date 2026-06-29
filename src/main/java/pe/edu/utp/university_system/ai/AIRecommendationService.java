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

@RequiredArgsConstructor
@Service
public class AIRecommendationService {

    private final AIRecommendationRepository repository;

    private final AISettingsRepository settingsRepository;

    public void generateRecommendation(
            Enrollment enrollment,
            Evaluation evaluation,
            Double gradeValue) {

        AISettings settings =
                settingsRepository.findById(1L)
                        .orElseThrow();

        if (!settings.getEnabled()) {
            return;
        }

        if (gradeValue >=
                settings.getMinimumGradeThreshold()) {
            return;
        }

        if (evaluation.getEvaluationType()
                == EvaluationType.FINAL_EXAM) {
            return;
        }

        List<String> topics =
                getTopicsForReinforcement(
                        evaluation
                );

        String prompt =
                settings.getSystemPrompt()
                        .replace("{course}",
                                evaluation
                                        .getModule()
                                        .getCourse()
                                        .getName())
                        .replace("{grade}",
                                String.valueOf(
                                        gradeValue))
                        .replace("{evaluation}",
                                evaluation
                                        .getTitle())
                        .replace("{topics}",
                                String.join(
                                        ", ",
                                        topics));

        String aiResponse = "";

        AIRecommendation recommendation = new AIRecommendation();
        recommendation.setEnrollment(enrollment);
        recommendation.setGradeObtained(gradeValue);
        recommendation.setEvaluationTitle(evaluation.getTitle());
        recommendation.setModelName(settings.getModelName());
        recommendation.setPrompt(prompt);
        recommendation.setRecommendationContent(aiResponse);
        recommendation.setStatus(RecommendationStatus.GENERATED);
        recommendation.setReadByStudent(false);
        recommendation.setGeneratedAt(LocalDateTime.now());

        repository.save(recommendation);
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