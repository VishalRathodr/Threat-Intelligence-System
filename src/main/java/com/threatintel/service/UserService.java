package com.threatintel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threatintel.entity.User;
import com.threatintel.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===========================
    // Register User
    // ===========================

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (userRepository.existsByMobile(user.getMobile())) {
            throw new RuntimeException("Mobile number already exists.");
        }

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        // If username is empty, use email as username
        if (user.getUsername() == null ||
            user.getUsername().isEmpty()) {

            user.setUsername(user.getEmail());
        }

        return userRepository.save(user);
    }

    // ===========================
    // Login User
    // ===========================

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    // ===========================
    // Get All Users
    // ===========================

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // ===========================
    // Get User By Id
    // ===========================

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // ===========================
    // Update User
    // ===========================

    public User updateUser(User user) {

        return userRepository.save(user);
    }

    // ===========================
    // Delete User
    // ===========================

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    // ===========================
    // Total Users
    // ===========================

    public long getTotalUsers() {

        return userRepository.count();
    }

    // ===========================
    // Active Users
    // ===========================

    public long getActiveUsers() {

        return userRepository.countByEnabled(true);
    }
}