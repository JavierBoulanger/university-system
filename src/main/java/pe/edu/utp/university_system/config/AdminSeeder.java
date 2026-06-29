package pe.edu.utp.university_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.utp.university_system.auth.*;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserAccountRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (repository.findByUsername("j4v").isPresent()) {

            System.out.println("Administrador predeterminado ya existe");
            return;
        }

        UserAccount admin = new UserAccount();

        admin.setUsername("j4v");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        repository.save(admin);
        System.out.println("Administrador predeterminado j4v creado");
    }
}