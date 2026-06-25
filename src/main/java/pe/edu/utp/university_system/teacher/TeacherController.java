package pe.edu.utp.university_system.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(@RequestBody TeacherRegistrationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teacherService.createTeacher(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}