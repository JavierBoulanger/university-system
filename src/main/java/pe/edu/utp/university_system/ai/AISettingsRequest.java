package pe.edu.utp.university_system.ai;

public record AISettingsRequest(

        Boolean enabled,

        String provider,

        String modelName,

        Double temperature,

        Double minimumGradeThreshold,

        Integer maxOutputTokens,

        String systemPrompt

) {
}