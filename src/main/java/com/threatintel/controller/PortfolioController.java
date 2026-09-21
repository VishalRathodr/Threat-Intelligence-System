package com.threatintel.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.threatintel.entity.User;
import com.threatintel.service.UserService;

@Controller
public class PortfolioController {

    private final UserService userService;

    public PortfolioController(UserService userService) {
        this.userService = userService;
    }

    // ===============================
    // Portfolio Home
    // ===============================
    @GetMapping("/")
    public String index() {
        return "portfolio/index";
    }

    // ===============================
    // About
    // ===============================
    @GetMapping("/about")
    public String about() {
        return "portfolio/about";
    }

    // ===============================
    // Services
    // ===============================
    @GetMapping("/services")
    public String services() {
        return "portfolio/services";
    }

    // ===============================
    // Projects
    // ===============================
    @GetMapping("/projects")
    public String projects() {
        return "portfolio/projects";
    }

    // ===============================
    // Contact
    // ===============================
    @GetMapping("/contact")
    public String contact() {
        return "portfolio/contact";
    }

    // ===============================
    // Threat Information
    // ===============================
    @GetMapping("/threat-info")
    public String threatInfo() {
        return "portfolio/threat-info";
    }

    // ===============================
    // Profile
    // ===============================
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {

        if (authentication != null) {

            Optional<User> user =
                    userService.getUserByEmail(authentication.getName());

            user.ifPresent(value ->
                    model.addAttribute("user", value));
        }

        return "user/profile";
    }

    // ===============================
    // Login Selection
    // ===============================
    @GetMapping("/login")
    public String login() {
        return "login/select-login";
    }
}