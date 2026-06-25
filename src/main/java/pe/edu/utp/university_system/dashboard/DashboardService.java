package pe.edu.utp.university_system.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.enrollment.Enrollment;
import pe.edu.utp.university_system.enrollment.EnrollmentRepository;
import pe.edu.utp.university_system.auth.UserAccount;
import pe.edu.utp.university_system.auth.UserAccountRepository;
import pe.edu.utp.university_system.section.SectionRepository;
import pe.edu.utp.university_system.student.Student;
import pe.edu.utp.university_system.student.StudentRepository;
import pe.edu.utp.university_system.teacher.Teacher;
import pe.edu.utp.university_system.teacher.TeacherRepository;
import pe.edu.utp.university_system.section.Section;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserAccountRepository userAccountRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SectionRepository sectionRepository;

    public List<DashboardCourseResponse> getCourses(Long accountId) {

        UserAccount account = userAccountRepository.findById(accountId)
                .orElseThrow();

        switch (account.getRole()) {

            case STUDENT:
                return getStudentCourses(accountId);

            case TEACHER:
                return getTeacherCourses(accountId);

            default:
                throw new RuntimeException("Role not supported");
        }
    }

    private List<DashboardCourseResponse> getStudentCourses(Long accountId) {

        Student student = studentRepository
                .findByUserAccountId(accountId)
                .orElseThrow();

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(student.getId());

        return enrollments.stream()
                .map(enrollment -> {

                    Section section = enrollment.getSection();

                    return new DashboardCourseResponse(
                            section.getCourse().getId(),
                            section.getCourse().getName(),
                            section.getSectionName(),
                            section.getTeacher().getFirstName()
                                    + " "
                                    + section.getTeacher().getLastName()
                    );

                })
                .toList();
    }

    private List<DashboardCourseResponse> getTeacherCourses(Long accountId) {

        Teacher teacher = teacherRepository
                .findByUserAccountId(accountId)
                .orElseThrow();

        List<Section> sections =
                sectionRepository.findByTeacherId(teacher.getId());

        return sections.stream()
                .map(section ->
                        new DashboardCourseResponse(
                                section.getCourse().getId(),
                                section.getCourse().getName(),
                                section.getSectionName(),
                                teacher.getFirstName()
                                        + " "
                                        + teacher.getLastName()
                        )
                )
                .toList();
    }
}