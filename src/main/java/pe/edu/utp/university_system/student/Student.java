package pe.edu.utp.university_system.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Student")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String enrollmentCode;
    
    @Column(length = 15)
    private String phone;

    @Column(nullable = false, unique = true, length = 20)
    private String documentNumber;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = false)
    private LocalDate enrollmentDate;
    
    @Column(nullable = false)
    private Boolean active = true;
}