package pe.edu.utp.university_system.enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(
            @RequestBody EnrollmentRegistrationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(enrollmentService.createEnrollment(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollment(id));
    }
    
    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getEnrollments());
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponse>> getStudentEnrollments(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                enrollmentService.getStudentEnrollments(studentId));
    }
}