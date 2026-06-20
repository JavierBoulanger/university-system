package pe.edu.utp.university_system.enrollment;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    long countBySectionId(Long sectionId);
    
    boolean existsByStudentIdAndSectionId(
        Long studentId,
        Long sectionId
    );

    List<Enrollment> findByStudentId(Long studentId);

}
