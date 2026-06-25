package pe.edu.utp.university_system.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}