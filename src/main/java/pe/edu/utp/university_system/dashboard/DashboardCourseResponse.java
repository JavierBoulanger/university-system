package pe.edu.utp.university_system.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardCourseResponse {

    private Long courseId;

    private String nombreCurso;

    private String codigoSeccion;

    private String profesorNombre;
}