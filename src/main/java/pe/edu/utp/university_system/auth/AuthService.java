package pe.edu.utp.university_system.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) {

        UserAccount account =
                repository.findByUsername(
                                request.getUsername())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid credentials"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                account.getPassword())) {

            throw new RuntimeException(
                    "Invalid credentials");
        }

        if (!account.getActive()) {
            throw new RuntimeException(
                    "Account is inactive");
        }

        LoginResponse response = new LoginResponse();
        response.setAccountId(account.getId());
        response.setUsername(account.getUsername());
        response.setRole(account.getRole().name());
        response.setMessage("Login successful");

        return response;
    }
}