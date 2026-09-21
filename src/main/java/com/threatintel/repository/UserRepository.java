package com.threatintel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Login
    Optional<User> findByEmail(String email);

    // Register Validation
    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    // Profile Search
    Optional<User> findByMobile(String mobile);

    // Dashboard
    long countByEnabled(boolean enabled);

}