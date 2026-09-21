package com.threatintel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    private String protocol;

    private Integer port;

    private Integer requestCount;

    private String predictedAttack;

    private String riskLevel;

    private Double confidence;

    private String predictionResult;

    private String modelName;

    private LocalDateTime predictionTime;

    public Prediction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public String getPredictedAttack() {
        return predictedAttack;
    }

    public void setPredictedAttack(String predictedAttack) {
        this.predictedAttack = predictedAttack;
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

    public String getPredictionResult() {
        return predictionResult;
    }

    public void setPredictionResult(String predictionResult) {
        this.predictionResult = predictionResult;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public LocalDateTime getPredictionTime() {
        return predictionTime;
    }

    public void setPredictionTime(LocalDateTime predictionTime) {
        this.predictionTime = predictionTime;
    }
}