package pe.edu.utp.university_system.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.auth.AccountService;
import pe.edu.utp.university_system.auth.UserAccount;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final AccountService accountService;

    public List<TeacherResponse> getTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher =
                teacherRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Teacher not found"));

        return toResponse(teacher);
    }

    public TeacherResponse createTeacher(
            TeacherRegistrationRequest request) {

        UserAccount account = accountService.createTeacherAccount(request.getTeacherCode());

        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setTeacherCode(request.getTeacherCode());
        teacher.setSpecialty(request.getSpecialty());
        teacher.setPhone(request.getPhone());
        teacher.setDocumentNumber(request.getDocumentNumber());
        teacher.setHireDate(request.getHireDate());
        teacher.setAcademicDegree(request.getAcademicDegree());
        teacher.setActive(true);
        teacher.setUserAccount(account);

        Teacher saved = teacherRepository.save(teacher);

        return toResponse(saved);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    private TeacherResponse toResponse(Teacher teacher) {

        return new TeacherResponse(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getTeacherCode(),
                teacher.getEmail(),
                teacher.getSpecialty(),
                teacher.getAcademicDegree(),
                teacher.getActive()
        );
    }
}