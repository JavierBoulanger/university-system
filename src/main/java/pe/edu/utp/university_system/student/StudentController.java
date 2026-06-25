package pe.edu.utp.university_system.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse>
    createStudent(@RequestBody StudentRegistrationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.createStudent(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {studentService.deleteStudent(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}