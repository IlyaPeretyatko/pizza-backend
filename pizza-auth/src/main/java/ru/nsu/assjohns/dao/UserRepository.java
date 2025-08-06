package ru.nsu.assjohns.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.assjohns.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
    User findByVerificationCode(String email);
}
