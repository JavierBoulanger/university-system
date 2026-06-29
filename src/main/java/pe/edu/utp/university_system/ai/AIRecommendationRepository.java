package pe.edu.utp.university_system.ai;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRecommendationRepository
        extends JpaRepository<
        AIRecommendation,
        Long> {

    @Query("""
        SELECT r
        FROM AIRecommendation r
        WHERE r.enrollment.student.userAccount.id =
              :accountId
        ORDER BY r.generatedAt DESC
    """)
    List<AIRecommendation>
    findByStudentAccountId(
            @Param("accountId")
            Long accountId
    );

    @Query("""
        SELECT COUNT(r)
        FROM AIRecommendation r
        WHERE r.enrollment.student.userAccount.id =
              :accountId
          AND r.readByStudent = false
    """)
    Long countUnreadRecommendations(
            @Param("accountId")
            Long accountId
    );

    @Modifying
    @Transactional
    @Query("""
        UPDATE AIRecommendation r
           SET r.readByStudent = true
         WHERE r.enrollment.student.userAccount.id =
               :accountId
    """)
    void markAllAsRead(
            @Param("accountId")
            Long accountId
    );
}