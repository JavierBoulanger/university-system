package pe.edu.utp.university_system.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @RequestBody CourseRegistrationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.createCourse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CourseDetailsResponse> getCourseDetails(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseDetails(id));
    }
}