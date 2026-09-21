package com.threatintel.controller.gmail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.threatintel.service.EmailService;

@Controller
@RequestMapping("/admin")
public class EmailAlertController {

    private final EmailService emailService;

    public EmailAlertController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-alert")
    public String sendAlert(@RequestParam String attackType,
                            @RequestParam String ip,
                            @RequestParam String riskLevel) {
        try {
            // to wala parameter add kiya
            String to = "vishalrathodr@gmail.com";
            emailService.sendAlert(to, attackType, ip, riskLevel);
            
        } catch (Exception e) {
            System.out.println("Mail failed: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}