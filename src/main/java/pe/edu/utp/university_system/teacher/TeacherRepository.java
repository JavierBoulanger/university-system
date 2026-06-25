package pe.edu.utp.university_system.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByTeacherCode(String teacherCode);
    Optional<Teacher> findByUserAccountId(Long userAccountId);
}