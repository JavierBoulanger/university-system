package pe.edu.utp.university_system.grading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.utp.university_system.course.Evaluation;
import pe.edu.utp.university_system.course.EvaluationRepository;
import pe.edu.utp.university_system.enrollment.Enrollment;
import pe.edu.utp.university_system.enrollment.EnrollmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final EvaluationRepository evaluationRepository;
    private final EnrollmentRepository enrollmentRepository;

    public void registerGrades(
            BulkGradeRegistrationRequest request) {

        Evaluation evaluation = evaluationRepository.findById(
                        request.getEvaluationId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evaluation not found"));

        List<Grade> gradesToSave = new ArrayList<>();

        for (StudentGradeRequest gradeRequest :
                request.getGrades()) {

            Enrollment enrollment =
                    enrollmentRepository.findById(
                                    gradeRequest.getEnrollmentId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Enrollment not found: "
                                                    + gradeRequest.getEnrollmentId()));

            if (gradeRepository
                    .existsByEnrollmentIdAndEvaluationId(
                            enrollment.getId(),
                            evaluation.getId())) {

                throw new RuntimeException(
                        "Grade already exists for enrollment "
                                + enrollment.getId()
                                + " and evaluation "
                                + evaluation.getId());
            }

            Grade grade = new Grade();

            grade.setValue(
                    gradeRequest.getValue());

            grade.setEnrollment(
                    enrollment);

            grade.setEvaluation(
                    evaluation);

            gradesToSave.add(grade);
        }

        gradeRepository.saveAll(gradesToSave);
    }

    public Double calculateCurrentAverage(
            Long enrollmentId) {

        List<Grade> grades =
                gradeRepository.findByEnrollmentId(
                        enrollmentId);

        double weightedAverage = 0.0;

        for (Grade grade : grades) {

            double weight =
                    grade.getEvaluation().getWeight()
                            / 100.0;

            weightedAverage +=
                    grade.getValue() * weight;
        }

        return Math.round(
                weightedAverage * 100.0)
                / 100.0;
    }


    /* para la IA futura
    public List<String> getTopicsNeedingReinforcement(
            Long enrollmentId,
            Double passingGrade) {
        return null;
    }
    */
}