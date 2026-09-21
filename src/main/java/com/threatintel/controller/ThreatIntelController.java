package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.threatintel.service.AttackLogService;

@Controller
@RequestMapping("/admin")
public class ThreatIntelController {

    private final AttackLogService attackLogService;

    public ThreatIntelController(AttackLogService attackLogService) {
        this.attackLogService = attackLogService;
    }

    // ==========================
    // Threat Intelligence
    // ==========================

    @GetMapping("/threat-intelligence")
    public String threatIntelligence(Model model) {

        model.addAttribute(
                "totalAttacks",
                attackLogService.getTotalAttacks()
        );

        model.addAttribute(
                "highRisk",
                attackLogService.getHighRiskAttacks()
        );

        model.addAttribute(
                "mediumRisk",
                attackLogService.getMediumRiskAttacks()
        );

        model.addAttribute(
                "lowRisk",
                attackLogService.getLowRiskAttacks()
        );

        model.addAttribute(
                "wordpress",
                attackLogService.getWordPressAttacks()
        );

        model.addAttribute(
                "sqlInjection",
                attackLogService.getSqlInjectionAttacks()
        );

        model.addAttribute(
                "xss",
                attackLogService.getXssAttacks()
        );

        model.addAttribute(
                "attacks",
                attackLogService.getLatestAttacks()
        );

        return "admin/threat-intelligence";
    }
}