package pe.edu.utp.university_system.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIRecommendationRepository
        extends JpaRepository<AIRecommendation, Long> {
}