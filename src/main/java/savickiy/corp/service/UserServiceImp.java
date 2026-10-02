package savickiy.corp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import savickiy.corp.dto.RegisterRequest;
import savickiy.corp.entity.enums.Theme;
import savickiy.corp.entity.jpa.User;
import savickiy.corp.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User register(RegisterRequest registration) {
        if (userRepository.existsByEmail(registration.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .email(registration.getEmail())
                .password(registration.getPassword())
                .theme(Theme.LIGHT)
                .notificationEnabled(true)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean exitsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User changeTheme(Long userId, String theme) {
        User user = findById(userId);
         user.setTheme(Theme.valueOf(theme.toUpperCase()));
         return userRepository.save(user);
    }
}
