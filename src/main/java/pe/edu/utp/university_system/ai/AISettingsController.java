package pe.edu.utp.university_system.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ai-settings")
@RequiredArgsConstructor
public class AISettingsController {

    private final AISettingsService service;

    @GetMapping
    public ResponseEntity<AISettingsResponse>
    getSettings() {

        return ResponseEntity.ok(
                service.getSettings()
        );
    }

    @PutMapping
    public ResponseEntity<AISettingsResponse>
    updateSettings(
            @RequestBody AISettingsRequest request) {

        return ResponseEntity.ok(
                service.updateSettings(request)
        );
    }
}