package pe.edu.utp.university_system.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private static final int TOTAL_MODULES = 18;

    private final CourseRepository courseRepository;

    public CourseResponse createCourse(CourseRegistrationRequest request) {

        Course course = new Course();
        course.setName(request.getName());
        course.setCourseCode(request.getCourseCode());
        course.setCredits(request.getCredits());
        course.setModules(new ArrayList<>());

        for (int i = 1; i <= TOTAL_MODULES; i++) {

            Module module = new Module();
            module.setModuleNumber(i);
            module.setCourse(course);
            module.setTopics(new ArrayList<>());
            module.setEvaluations(new ArrayList<>());

            final int currentModuleNumber = i;

            if (request.getModules() != null) {

                request.getModules().stream()
                        .filter(moduleRequest ->
                                moduleRequest.getModuleNumber() != null
                                        && moduleRequest.getModuleNumber()
                                        .equals(currentModuleNumber))
                        .findFirst()
                        .ifPresent(moduleRequest -> {

                            addTopics(module, moduleRequest);

                            addEvaluations(module, moduleRequest);
                        });
            }

            course.getModules().add(module);
        }

        validateEvaluationWeights(course);

        Course saved = courseRepository.save(course);

        return new CourseResponse(
                saved.getId(),
                saved.getName(),
                saved.getCourseCode(),
                saved.getCredits()
        );
    }

    public CourseResponse getCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getCourseCode(),
                course.getCredits()
        );
    }

    public CourseDetailsResponse getCourseDetails(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        List<ModuleResponse> modules = course.getModules().stream()
                .map(module -> {

                    List<TopicResponse> topics = module.getTopics().stream()
                            .map(topic -> new TopicResponse(
                                    topic.getId(),
                                    topic.getName()
                            ))
                            .toList();

                    List<EvaluationResponse> evaluations =
                            module.getEvaluations().stream()
                                    .map(evaluation -> new EvaluationResponse(
                                            evaluation.getId(),
                                            evaluation.getTitle(),
                                            evaluation.getWeight()
                                    ))
                                    .toList();

                    return new ModuleResponse(
                            module.getId(),
                            module.getModuleNumber(),
                            topics,
                            evaluations
                    );
                })
                .toList();

        return new CourseDetailsResponse(
                course.getId(),
                course.getName(),
                course.getCourseCode(),
                course.getCredits(),
                modules
        );
    }

    private void addTopics(
            Module module,
            ModuleRegistrationRequest request) {

        if (request.getTopics() == null) {
            return;
        }

        for (String topicName : request.getTopics()) {

            Topic topic = new Topic();
            topic.setName(topicName);
            topic.setModule(module);

            module.getTopics().add(topic);
        }
    }

    private void addEvaluations(
            Module module,
            ModuleRegistrationRequest request) {

        if (request.getEvaluations() == null) {
            return;
        }

        for (EvaluationRegistrationRequest evaluationRequest
                : request.getEvaluations()) {

            Evaluation evaluation = new Evaluation();

            evaluation.setTitle(
                    evaluationRequest.getTitle());

            evaluation.setWeight(
                    evaluationRequest.getWeight());

            evaluation.setModule(module);

            module.getEvaluations().add(evaluation);
        }
    }

    private void validateEvaluationWeights(Course course) {

        double totalWeight = course.getModules()
                .stream()
                .flatMap(module ->
                        module.getEvaluations().stream())
                .mapToDouble(Evaluation::getWeight)
                .sum();

        if (Double.compare(totalWeight, 100.0) != 0) {

            throw new IllegalArgumentException(
                    "The total evaluation weight must equal 100%. Current total: "
                            + totalWeight + "%"
            );
        }
    }
}