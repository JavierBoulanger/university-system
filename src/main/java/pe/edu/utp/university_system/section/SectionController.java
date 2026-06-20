package pe.edu.utp.university_system.section;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<SectionResponse> createSection(
            @RequestBody SectionRegistrationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sectionService.createSection(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SectionResponse> getSection(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sectionService.getSection(id));
    }
    
    @GetMapping
    public ResponseEntity<List<SectionResponse>> getSections() {

        return ResponseEntity.ok(
                sectionService.getSections());
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SectionResponse>> getSectionsByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                sectionService.getSectionsByCourse(courseId));
    }
}