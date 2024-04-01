package ru.melnikov.computershop.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.melnikov.computershop.model.user.User;

public interface UserService {

    User save(User user);

    User create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    User getCurrentUser();
}
