package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.service.AdminService;
import com.threatintel.service.AttackLogService;
import com.threatintel.service.UserService;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    private final UserService userService;
    private final AdminService adminService;
    private final AttackLogService attackLogService;

    public DashboardController(UserService userService,
                               AdminService adminService,
                               AttackLogService attackLogService) {

        this.userService = userService;
        this.adminService = adminService;
        this.attackLogService = attackLogService;
    }

    // ================= Dashboard =================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalAdmins", adminService.getTotalAdmins());
        model.addAttribute("totalAttacks", attackLogService.getTotalAttacks());

        model.addAttribute("highRisk", attackLogService.getHighRiskAttacks());
        model.addAttribute("mediumRisk", attackLogService.getMediumRiskAttacks());
        model.addAttribute("lowRisk", attackLogService.getLowRiskAttacks());

        model.addAttribute("wordpress", attackLogService.getWordPressAttacks());
        model.addAttribute("sqlInjection", attackLogService.getSqlInjectionAttacks());
        model.addAttribute("xss", attackLogService.getXssAttacks());

        model.addAttribute("attacks", attackLogService.getLatestAttacks());

        return "admin/dashboard";
    }

    // ================= Settings =================

    @GetMapping("/settings")
    public String settings(Model model) {

        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalAdmins", adminService.getTotalAdmins());
        model.addAttribute("totalAttacks", attackLogService.getTotalAttacks());

        return "admin/settings";
    }
}