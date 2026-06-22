package pe.edu.utp.university_system.grading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository
        extends JpaRepository<Grade, Long> {

    List<Grade> findByEnrollmentId(Long enrollmentId);

    boolean existsByEnrollmentIdAndEvaluationId(
            Long enrollmentId,
            Long evaluationId
    );
}