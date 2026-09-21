package com.threatintel.controller;

import java.time.LocalDateTime;
import java.util.Map; // add kiya

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.threatintel.entity.AttackLog;
import com.threatintel.service.AttackLogService;
import com.threatintel.service.GeolocationService;

@Controller
@RequestMapping("/honeypot")
public class HoneypotController {

    private final AttackLogService attackLogService;
    private final GeolocationService geolocationService;

    public HoneypotController(AttackLogService attackLogService,
                              GeolocationService geolocationService) {
        this.attackLogService = attackLogService;
        this.geolocationService = geolocationService;
    }

    @GetMapping("/login")
    public String fakeLoginPage() {
        return "honeypot/login";
    }

    @PostMapping("/login")
    public String captureLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        AttackLog attack = new AttackLog();
        attack.setIpAddress(ip);
        attack.setUsername(username);
        attack.setPassword(password);
        attack.setAttackType("Brute Force");
        attack.setRiskLevel("HIGH");
        attack.setRequestMethod(request.getMethod());
        attack.setRequestUri(request.getRequestURI()); // FIX 1: setUri -> setRequestUri
        attack.setUserAgent(userAgent);
        attack.setTimestamp(LocalDateTime.now());

        Map<String, Object> location = geolocationService.getLocation(ip); // FIX 2: Type diya

        if(location != null){
            attack.setCountry((String) location.getOrDefault("country", "Unknown"));
            attack.setCity((String) location.getOrDefault("city", "Unknown"));
            attack.setRegion((String) location.getOrDefault("regionName", "Unknown"));
            attack.setLatitude((Double) location.get("lat"));
            attack.setLongitude((Double) location.get("lon"));
        } else {
            attack.setCountry("Unknown");
            attack.setCity("Unknown");
            attack.setRegion("Unknown");
        }

        attackLogService.saveAttack(attack);
        model.addAttribute("message", "Invalid Username or Password");
        return "honeypot/login";
    }
}