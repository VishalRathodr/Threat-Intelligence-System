package com.threatintel.dto;

public class PredictionResult {

    private String attackType;
    private String riskLevel;
    private Double confidence;
    private String recommendation;

    public PredictionResult() {
    }

    public PredictionResult(String attackType,
                            String riskLevel,
                            Double confidence,
                            String recommendation) {
        this.attackType = attackType;
        this.riskLevel = riskLevel;
        this.confidence = confidence;
        this.recommendation = recommendation;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}