package pe.edu.utp.university_system.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.auth.AccountService;
import pe.edu.utp.university_system.auth.UserAccount;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AccountService accountService;

    public List<StudentResponse> getStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(
            Long id) {

        Student student =
                studentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found"));

        return toResponse(student);
    }

    public StudentResponse createStudent(
            StudentRegistrationRequest request) {

        UserAccount account =
                accountService.createStudentAccount(
                        request.getEnrollmentCode());

        Student student =
                new Student();

        student.setFirstName(
                request.getFirstName());

        student.setLastName(
                request.getLastName());

        student.setEmail(
                request.getEmail());

        student.setEnrollmentCode(
                request.getEnrollmentCode());

        student.setPhone(
                request.getPhone());

        student.setDocumentNumber(
                request.getDocumentNumber());

        student.setBirthDate(
                request.getBirthDate());

        student.setEnrollmentDate(
                LocalDate.now());

        student.setActive(true);

        student.setUserAccount(
                account);

        Student saved =
                studentRepository.save(student);

        return toResponse(saved);
    }

    public void deleteStudent(
            Long id) {

        studentRepository.deleteById(id);
    }

    private StudentResponse toResponse(
            Student student) {

        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEnrollmentCode(),
                student.getEmail(),
                student.getActive()
        );
    }
}