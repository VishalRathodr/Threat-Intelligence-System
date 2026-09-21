package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================
    // Registered Users
    // ==========================

    @GetMapping("/users")
    public String users(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "totalUsers",
                userService.getTotalUsers()
        );

        return "admin/users";
    }

    // ==========================
    // Delete User
    // ==========================

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }
}