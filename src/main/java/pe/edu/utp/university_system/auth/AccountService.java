package pe.edu.utp.university_system.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserAccount createStudentAccount(
            String studentCode) {

        UserAccount account = new UserAccount();
        account.setUsername(studentCode);
        account.setPassword(passwordEncoder.encode(studentCode));
        account.setRole(Role.STUDENT);
        account.setActive(true);

        return userAccountRepository.save(account);
    }

    public UserAccount createTeacherAccount(
            String teacherCode) {

        UserAccount account = new UserAccount();
        account.setUsername(teacherCode);
        account.setPassword(passwordEncoder.encode(teacherCode));
        account.setRole(Role.TEACHER);
        account.setActive(true);

        return userAccountRepository.save(account);
    }

    public UserAccount createAdminAccount(
            String username,
            String password) {

        UserAccount account = new UserAccount();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(Role.ADMIN);
        account.setActive(true);

        return userAccountRepository.save(account);
    }
}