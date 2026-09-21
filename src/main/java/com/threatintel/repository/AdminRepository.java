package com.threatintel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Admin Login
    Optional<Admin> findByEmail(String email);

    // Register Validation
    boolean existsByEmail(String email);

    // Admin Code Validation
    Optional<Admin> findByAdminCode(String adminCode);

    // Dashboard
    long countByEnabled(boolean enabled);

}