package pe.edu.utp.university_system.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEnrollmentCode(String enrollmentCode);
    Optional<Student> findByUserAccountId(Long userAccountId);
}