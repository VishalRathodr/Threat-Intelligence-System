package com.threatintel.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "ADMINS")
@SequenceGenerator(name = "admin_seq", sequenceName = "ADMIN_SEQ", allocationSize = 1)
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
    @Column(name = "FULL_NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "ADMIN_CODE", nullable = false, length = 50) // <- insertable=false updatable=false hata diya
    private String adminCode;
    
    @Column(name = "ROLE", nullable = false, length = 20)
    private String role;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    public Admin() {}

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        enabled = true;

        if (role == null || role.isBlank()) {
            role = "ROLE_ADMIN"; // <- Spring Security ke liye ROLE_ lagana zaroori
        }

        if (adminCode == null || adminCode.isBlank()) {
            adminCode = "ADM" + System.currentTimeMillis(); // <- auto generate kar diya
        }
    }

    // getters setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}