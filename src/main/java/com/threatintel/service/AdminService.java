package com.threatintel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threatintel.entity.Admin;
import com.threatintel.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================
    // Register Admin
    // ==========================

    public Admin registerAdmin(Admin admin) {

        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_ADMIN");
        admin.setEnabled(true);
        admin.setCreatedAt(LocalDateTime.now());

        return adminRepository.save(admin);
    }

    // ==========================
    // Get All Admins
    // ==========================

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // ==========================
    // Get Admin By Id
    // ==========================

    public Admin getAdminById(Long id) {

        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin Not Found"));
    }

    // ==========================
    // Update Admin
    // ==========================

    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // ==========================
    // Delete Admin
    // ==========================

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    // ==========================
    // Dashboard Count
    // ==========================

    public long getTotalAdmins() {
        return adminRepository.count();
    }

    public long getActiveAdmins() {
        return adminRepository.countByEnabled(true);
    }

}