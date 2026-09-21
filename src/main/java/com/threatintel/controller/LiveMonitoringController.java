package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.threatintel.service.AttackLogService;

@Controller
public class LiveMonitoringController {

    private final AttackLogService attackLogService;

    public LiveMonitoringController(AttackLogService attackLogService) {
        this.attackLogService = attackLogService;
    }

    @GetMapping("/admin/live-monitoring")
    public String liveMonitoring(Model model) {

        model.addAttribute("totalAttacks",
                attackLogService.getTotalAttacks());

        model.addAttribute("highRisk",
                attackLogService.getHighRiskAttacks());

        model.addAttribute("mediumRisk",
                attackLogService.getMediumRiskAttacks());

        model.addAttribute("lowRisk",
                attackLogService.getLowRiskAttacks());

        model.addAttribute("attacks",
                attackLogService.getLatestAttacks());

        return "admin/live-monitoring";
    }
}