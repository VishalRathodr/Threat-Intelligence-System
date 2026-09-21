package com.threatintel.controller;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.threatintel.service.AttackLogService;

@Controller
@RequestMapping("/admin")
public class AttackLogController {

    private final AttackLogService attackLogService;

    public AttackLogController(AttackLogService attackLogService) {
        this.attackLogService = attackLogService;
    }

    // ==========================
    // All Attack Logs
    // ==========================

    @GetMapping("/attack-logs")
    public String attackLogs(Model model) {

        model.addAttribute(
                "attacks",
                attackLogService.getAllAttacks()
        );

        return "admin/attack-logs";
    }

    // ==========================
    // Search By IP
    // ==========================

    @GetMapping("/attack-logs/ip")
    public String searchByIp(
            @RequestParam String ip,
            Model model) {

        model.addAttribute(
                "attacks",
                attackLogService.getByIp(ip)
        );

        return "admin/attack-logs";
    }

    // ==========================
    // Search By Country
    // ==========================

    @GetMapping("/attack-logs/country")
    public String searchByCountry(
            @RequestParam String country,
            Model model) {

        model.addAttribute(
                "attacks",
                attackLogService.getByCountry(country)
        );

        return "admin/attack-logs";
    }

    // ==========================
    // Search By Risk
    // ==========================

    @GetMapping("/attack-logs/risk")
    public String searchByRisk(
            @RequestParam String risk,
            Model model) {

        model.addAttribute(
                "attacks",
                attackLogService.getByRiskLevel(risk)
        );

        return "admin/attack-logs";
    }

    // ==========================
    // Delete Attack
    // ==========================

    @GetMapping("/attack/delete/{id}")
    public String deleteAttack(@PathVariable Long id) {

        attackLogService.deleteAttack(id);

        return "redirect:/admin/attack-logs";
    }
    
 // ==========================
 // Export Attack Logs
 // ==========================

 @GetMapping("/attack-logs/export")
 public void exportAttackLogs(HttpServletResponse response) throws Exception {

     response.setContentType("text/csv");
     response.setHeader(
             "Content-Disposition",
             "attachment; filename=attack_logs.csv"
     );

     PrintWriter writer = response.getWriter();

     writer.println("ID,IP Address,Attack Type,Country,Risk Level,Username,Protocol,Timestamp");

     attackLogService.getAllAttacks().forEach(attack -> {

         writer.println(
             attack.getId() + "," +
             attack.getIpAddress() + "," +
             attack.getAttackType() + "," +
             attack.getCountry() + "," +
             attack.getRiskLevel() + "," +
             attack.getUsername() + "," +
             attack.getRequestMethod() + "," +
             attack.getTimestamp()
         );
     });

     writer.flush();
 }
}