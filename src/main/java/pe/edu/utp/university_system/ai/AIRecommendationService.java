package pe.edu.utp.university_system.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Value("${gemini.api.key}")
    private String apiKey;

    public void generateRecommendation(
            Enrollment enrollment,
            Evaluation evaluation,
            Double gradeValue) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models?key="
                        + apiKey;

        System.out.println(
                new RestTemplate().getForObject(
                        url,
                        String.class
                )
        );

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

        AIRecommendation recommendation = new AIRecommendation();
        recommendation.setEnrollment(enrollment);
        recommendation.setGradeObtained(gradeValue);
        recommendation.setEvaluationTitle(evaluation.getTitle());
        recommendation.setModelName(settings.getModelName());
        recommendation.setPrompt(prompt);
        recommendation.setReadByStudent(false);
        recommendation.setGeneratedAt(LocalDateTime.now());

        // integracion gemini
        try {
            String aiResponse = callGeminiApi(prompt, settings.getModelName(), settings.getTemperature());

            recommendation.setRecommendationContent(aiResponse);
            recommendation.setStatus(RecommendationStatus.GENERATED);
        }catch (Exception e) {
                e.printStackTrace();
                recommendation.setRecommendationContent("No fue posible generar la recomendación.");
                recommendation.setStatus(RecommendationStatus.FAILED);
            }

        repository.save(recommendation);
    }

    private String callGeminiApi(String prompt, String modelName, Double temperature) throws Exception {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent?key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Limpiar el prompt de posibles comillas dobles que rompan el JSON
        String cleanPrompt = prompt.replace("\"", "\\\"").replace("\n", " ");

        // Estructura JSON requerida por Google AI Studio
        String requestBody = """
            {
              "contents": [{
                "parts": [{
                  "text": "%s"
                }]
              }],
              "generationConfig": {
                "temperature": %s
              }
            }
            """.formatted(cleanPrompt, temperature);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());

        return rootNode.path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text")
                .asText();
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