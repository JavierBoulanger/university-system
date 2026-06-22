package pe.edu.utp.university_system.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository
        extends JpaRepository<Evaluation, Long> {
}