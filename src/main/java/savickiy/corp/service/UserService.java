package savickiy.corp.service;

import savickiy.corp.dto.RegisterRequest;
import savickiy.corp.entity.jpa.User;

import java.util.Optional;

public interface UserService {

    User register(RegisterRequest registration);

    User findById(Long id);

    Optional<User> findByEmail(String email);

    boolean exitsEmail(String email);

    User changeTheme(Long userId, String theme);
}
