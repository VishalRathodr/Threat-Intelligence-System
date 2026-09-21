package com.threatintel.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.threatintel.dto.PredictionResult;
import com.threatintel.entity.Prediction;
import com.threatintel.service.PredictionService;

@Controller
@RequestMapping("/admin")
public class PredictionController {

    private final PredictionService predictionService;

    // Saved IP Addresses
    private final List<String> savedIps = Arrays.asList(
            "10.99.11.209",
            "192.168.1.10",
            "192.168.1.20",
            "172.16.0.50",
            "8.8.8.8"
    );

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    // Open Prediction Page
    @GetMapping("/prediction")
    public String predictionPage(Model model) {

        model.addAttribute("savedIps", savedIps);

        return "admin/prediction";
    }

    // Predict Attack
    @PostMapping("/prediction")
    public String predictAttack(
            @RequestParam String protocol,
            @RequestParam String ipAddress,
            @RequestParam Integer port,
            @RequestParam Integer requestCount,
            Model model) {

        // AI Prediction
        PredictionResult result = predictionService.predict(
                protocol,
                ipAddress,
                port,
                requestCount
        );

        // Save Prediction
        Prediction prediction = new Prediction();

        prediction.setIpAddress(ipAddress);
        prediction.setProtocol(protocol);
        prediction.setPort(port);
        prediction.setRequestCount(requestCount);

        prediction.setPredictedAttack(
                result.getAttackType()
        );

        prediction.setRiskLevel(
                result.getRiskLevel()
        );

        prediction.setConfidence(
                result.getConfidence()
        );

        prediction.setPredictionResult("COMPLETED");

        prediction.setModelName("Rule Based AI");

        prediction.setPredictionTime(
                LocalDateTime.now()
        );

        predictionService.savePrediction(prediction);

        // Send result to HTML
        model.addAttribute("result", result);

        // Send saved IPs again
        model.addAttribute("savedIps", savedIps);

        return "admin/prediction";
    }
}