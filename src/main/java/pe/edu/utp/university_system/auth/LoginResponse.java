package pe.edu.utp.university_system.auth;

import lombok.Data;

@Data
public class LoginResponse {

    private Long accountId;

    private String username;

    private String role;

    private Long studentId;

    private Long teacherId;

    private String message;
}