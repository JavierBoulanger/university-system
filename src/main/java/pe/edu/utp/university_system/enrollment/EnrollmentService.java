package pe.edu.utp.university_system.enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.section.Section;
import pe.edu.utp.university_system.section.SectionRepository;
import pe.edu.utp.university_system.student.Student;
import pe.edu.utp.university_system.student.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;

    public EnrollmentResponse createEnrollment(
            EnrollmentRegistrationRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        if (enrollmentRepository.existsByStudentIdAndSectionId(
                student.getId(),
                section.getId())) {

            throw new RuntimeException(
                    "Student is already enrolled in this section");
        }

        long currentEnrollments =
                enrollmentRepository.countBySectionId(section.getId());

        if (currentEnrollments >= section.getMaxCapacity()) {
            throw new RuntimeException("Maximum section capacity reached");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setSection(section);
        enrollment.setAcademicTerm(request.getAcademicTerm());
        enrollment.setEnrollmentDate(LocalDate.now());

        Enrollment saved = enrollmentRepository.save(enrollment);

        return new EnrollmentResponse(
                saved.getId(),
                saved.getStudent().getId(),
                saved.getSection().getId(),
                saved.getAcademicTerm()
        );
    }

    public List<EnrollmentResponse> getEnrollments() {

        return enrollmentRepository.findAll()
                .stream()
                .map(enrollment -> new EnrollmentResponse(
                        enrollment.getId(),
                        enrollment.getStudent().getId(),
                        enrollment.getSection().getId(),
                        enrollment.getAcademicTerm()
                ))
                .toList();
    }

    public EnrollmentResponse getEnrollment(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Enrollment not found"));

        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getSection().getId(),
                enrollment.getAcademicTerm()
        );
    }

    public List<EnrollmentResponse> getStudentEnrollments(Long studentId) {

        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(enrollment -> new EnrollmentResponse(
                        enrollment.getId(),
                        enrollment.getStudent().getId(),
                        enrollment.getSection().getId(),
                        enrollment.getAcademicTerm()
                ))
                .toList();
    }
}