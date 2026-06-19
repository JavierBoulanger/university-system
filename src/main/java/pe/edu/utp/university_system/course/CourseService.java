package pe.edu.utp.university_system.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private static final int TOTAL_MODULES = 18;

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

            final int currentModuleNumber = i;

            if (request.getModules() != null) {
                request.getModules().stream()
                        .filter(m ->
                                m.getModuleNumber() != null &&
                                m.getModuleNumber().equals(currentModuleNumber))
                        .findFirst()
                        .ifPresent(moduleRequest -> {

                            if (moduleRequest.getTopics() != null) {

                                for (String topicName : moduleRequest.getTopics()) {

                                    Topic topic = new Topic();
                                    topic.setName(topicName);
                                    topic.setModule(module);

                                    module.getTopics().add(topic);
                                }
                            }
                        });
            }

            course.getModules().add(module);
        }

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
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getCourseCode(),
                course.getCredits()
        );
    }

    public CourseDetailsResponse getCourseDetails(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<ModuleResponse> modules = course.getModules().stream()
                .map(module -> {

                    List<TopicResponse> topics = module.getTopics().stream()
                            .map(topic -> new TopicResponse(
                                    topic.getId(),
                                    topic.getName()
                            ))
                            .toList();

                    return new ModuleResponse(
                            module.getId(),
                            module.getModuleNumber(),
                            topics
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
}