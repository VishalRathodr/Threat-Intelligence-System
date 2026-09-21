package com.threatintel.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.entity.User;
import com.threatintel.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================
    // User Registration Page
    // ==========================

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "user/register";
    }

    // ==========================
    // Save User
    // ==========================

    @PostMapping("/register")
    public String saveUser(
            @ModelAttribute User user,
            Model model) {

        try {

            userService.registerUser(user);

            model.addAttribute(
                    "success",
                    "Registration Successful"
            );

            return "user/login";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            model.addAttribute(
                    "user",
                    user
            );

            return "user/register";
        }
    }

    // ==========================
    // User Login Page
    // ==========================

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    // ==========================
    // User Dashboard
    // ==========================

    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            Model model) {

        User user = userService
                .getUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        model.addAttribute(
                "user",
                user
        );

        return "user/dashboard";
    }

    // ==========================
    // User Profile
    // ==========================

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {

        if (authentication == null) {
            return "redirect:/user/login";
        }

        String email = authentication.getName();
        System.out.println("Logged in email: " + email); // log check kar

        var optionalUser = userService.getUserByEmail(email);

        if (optionalUser.isEmpty()) {
            System.out.println("User not found in DB for: " + email);
            model.addAttribute("error", "User not found for: " + email + " - Please register again");
            return "user/login"; 
        }

        model.addAttribute("user", optionalUser.get());
        return "user/profile";
    }
}