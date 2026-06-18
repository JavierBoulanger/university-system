package pe.edu.utp.university_system.teacher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String teacherCode;

    @Column(length = 100)
    private String specialty;

    @Column(length = 15)
    private String phone;

    @Column(unique = true, length = 20)
    private String documentNumber;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(length = 50)
    private String academicDegree;

    @Column(nullable = false)
    private Boolean active = true;
}
