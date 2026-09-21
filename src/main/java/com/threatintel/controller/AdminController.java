package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ==========================
    // Admin Login Page
    // ==========================

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    // ==========================
    // Admin List Page
    // ==========================

    @GetMapping("/admins")
    public String admins(Model model) {

        model.addAttribute("admins",
                adminService.getAllAdmins());

        model.addAttribute("totalAdmins",
                adminService.getTotalAdmins());

        return "admin/admins";
    }

    // ==========================
    // Delete Admin
    // ==========================

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {

        adminService.deleteAdmin(id);

        return "redirect:/admin/admins";
    }
    
    
    
}