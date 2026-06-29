package pe.edu.utp.university_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.utp.university_system.ai.*;

@Component
@RequiredArgsConstructor
public class AISettingsSeeder implements CommandLineRunner {

    private final AISettingsRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        AISettings settings = new AISettings();
        settings.setEnabled(true);
        settings.setProvider("GEMINI");
        settings.setModelName("gemini-1.5-flash");
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

        repository.save(settings);
    }
}