package com.threatintel.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.threatintel.entity.Admin;
import com.threatintel.entity.User;
import com.threatintel.repository.AdminRepository;
import com.threatintel.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {

            User u = user.get();

            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getEmail())
                    .password(u.getPassword())
                    .authorities("ROLE_USER")
                    .disabled(!Boolean.TRUE.equals(u.getEnabled()))
                    .build();
        }

        Optional<Admin> admin = adminRepository.findByEmail(email);

        if (admin.isPresent()) {

            Admin a = admin.get();

            return org.springframework.security.core.userdetails.User
                    .withUsername(a.getEmail())
                    .password(a.getPassword())
                    .authorities("ROLE_ADMIN")
                    .disabled(!a.isEnabled())
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + email);
    }
}