package com.threatintel.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.threatintel.dto.PredictionResult;
import com.threatintel.entity.Prediction;
import com.threatintel.repository.PredictionRepository;

@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;

    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    // Save Prediction
    public Prediction savePrediction(Prediction prediction) {

        if (prediction.getPredictionTime() == null) {
            prediction.setPredictionTime(LocalDateTime.now());
        }

        return predictionRepository.save(prediction);
    }

    // AI Threat Prediction
    public PredictionResult predict(
            String protocol,
            String ipAddress,
            Integer port,
            Integer requestCount) {

        String attackType = "Normal";
        String riskLevel = "LOW";
        double confidence = 70.0;
        String recommendation = "No immediate action required.";

        // Rule 1: DDoS Attack
        if (requestCount >= 500) {

            attackType = "DDoS";
            riskLevel = "HIGH";
            confidence = 98.0;
            recommendation =
                    "Block IP immediately and enable rate limiting.";
        }

        // Rule 2: SSH Brute Force
        else if (port == 22) {

            attackType = "Brute Force";
            riskLevel = "HIGH";
            confidence = 95.0;
            recommendation =
                    "Block SSH login attempts.";
        }

        // Rule 3: MySQL Attack
        else if (port == 3306) {

            attackType = "SQL Injection";
            riskLevel = "HIGH";
            confidence = 93.0;
            recommendation =
                    "Inspect SQL queries and enable WAF.";
        }

        // Rule 4: HTTP XSS Attack
        else if ("HTTP".equalsIgnoreCase(protocol)
                && requestCount > 150) {

            attackType = "XSS Attack";
            riskLevel = "MEDIUM";
            confidence = 88.0;
            recommendation =
                    "Validate user input and enable input filtering.";
        }

        // Normal Traffic
        else {

            attackType = "Normal";
            riskLevel = "LOW";
            confidence = 70.0;
            recommendation =
                    "No immediate action required.";
        }

        PredictionResult result = new PredictionResult();

        result.setAttackType(attackType);
        result.setRiskLevel(riskLevel);
        result.setConfidence(confidence);
        result.setRecommendation(recommendation);

        return result;
    }
}