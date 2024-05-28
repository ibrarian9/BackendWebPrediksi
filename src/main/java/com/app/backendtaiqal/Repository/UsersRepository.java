package com.app.backendtaiqal.Repository;

import com.app.backendtaiqal.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmail(String email);
}

