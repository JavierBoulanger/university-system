package pe.edu.utp.university_system.grading;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/bulk")
    public ResponseEntity<String> registerGrades(
            @RequestBody
            BulkGradeRegistrationRequest request) {

        gradeService.registerGrades(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Grades registered successfully");
    }

    @GetMapping("/average/{enrollmentId}")
    public ResponseEntity<Double> getAverage(
            @PathVariable Long enrollmentId) {

        return ResponseEntity.ok(
                gradeService.calculateCurrentAverage(
                        enrollmentId));
    }
}