package ru.melnikov.computershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melnikov.computershop.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
