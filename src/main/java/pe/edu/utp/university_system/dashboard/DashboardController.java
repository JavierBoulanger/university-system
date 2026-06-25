package pe.edu.utp.university_system.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/courses/{accountId}")
    public ResponseEntity<List<DashboardCourseResponse>> getCourses(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                dashboardService.getCourses(accountId)
        );
    }
}