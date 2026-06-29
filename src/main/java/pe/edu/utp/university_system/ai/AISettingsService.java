package pe.edu.utp.university_system.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AISettingsService {

    private final AISettingsRepository repository;

    public AISettingsResponse getSettings() {

        AISettings settings =
                repository.findById(1L)
                        .orElseGet(this::createDefaultSettings);

        return mapToResponse(settings);
    }

    public AISettingsResponse updateSettings(
            AISettingsRequest request) {

        AISettings settings =
                repository.findById(1L)
                        .orElseGet(this::createDefaultSettings);

        settings.setEnabled(request.enabled());
        settings.setProvider(request.provider());
        settings.setModelName(request.modelName());
        settings.setTemperature(request.temperature());
        settings.setMinimumGradeThreshold(
                request.minimumGradeThreshold()
        );
        settings.setMaxOutputTokens(
                request.maxOutputTokens()
        );
        settings.setSystemPrompt(
                request.systemPrompt()
        );

        repository.save(settings);

        return mapToResponse(settings);
    }

    private AISettings createDefaultSettings() {

        AISettings settings = new AISettings();

        settings.setId(1L);
        settings.setEnabled(true);
        settings.setProvider("GEMINI");
        settings.setModelName("gemini-1.5-pro");
        settings.setTemperature(0.7);
        settings.setMinimumGradeThreshold(12.0);
        settings.setMaxOutputTokens(2048);

        settings.setSystemPrompt(
                """
                Eres un profesor universitario con experiencia.
        
                El estudiante obtuvo {grade}
                en la evaluación {evaluation}.
        
                Explica los siguientes temas:
        
                {topics}
        
                Luego proporciona:
        
                1. Una explicación concisa.
                2. Ejemplos prácticos.
                3. Dos ejercicios.
                4. Recomendaciones de estudio.
        
                Mantén un tono motivador.
                """
        );

        return repository.save(settings);
    }

    private AISettingsResponse mapToResponse(
            AISettings settings) {

        return new AISettingsResponse(
                settings.getEnabled(),
                settings.getProvider(),
                settings.getModelName(),
                settings.getTemperature(),
                settings.getMinimumGradeThreshold(),
                settings.getMaxOutputTokens(),
                settings.getSystemPrompt()
        );
    }
}