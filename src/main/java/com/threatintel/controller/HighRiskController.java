package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.service.AttackLogService;

@Controller
@RequestMapping("/admin")
public class HighRiskController {

    private final AttackLogService attackLogService;

    public HighRiskController(AttackLogService attackLogService) {
        this.attackLogService = attackLogService;
    }

    // ================= High Risk Attacks =================

    @GetMapping("/high-risk")
    public String highRisk(Model model) {

        model.addAttribute(
                "attacks",
                attackLogService.getHighRiskAttackLogs()
        );

        return "admin/high-risk";
    }
}