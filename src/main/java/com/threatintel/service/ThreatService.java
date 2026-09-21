package com.threatintel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.threatintel.entity.Threat;
import com.threatintel.repository.ThreatRepository;

@Service
public class ThreatService {

    private final ThreatRepository threatRepository;

    public ThreatService(ThreatRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    // ===============================
    // Save Threat
    // ===============================

    public Threat saveThreat(Threat threat) {

        if (threat.getCreatedAt() == null) {
            threat.setCreatedAt(LocalDateTime.now());
        }

        return threatRepository.save(threat);
    }

    // ===============================
    // Get All Threats
    // ===============================

    public List<Threat> getAllThreats() {
        return threatRepository.findAllByOrderByCreatedAtDesc();
    }

    // ===============================
    // Get Threat By ID
    // ===============================

    public Threat getThreatById(Long id) {

        return threatRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Threat Not Found"));

    }

    // ===============================
    // Search
    // ===============================

    public List<Threat> getByIp(String ip) {
        return threatRepository.findByIpAddress(ip);
    }

    public List<Threat> getByThreatName(String name) {
        return threatRepository.findByThreatName(name);
    }

    public List<Threat> getByAttackType(String attackType) {
        return threatRepository.findByAttackType(attackType);
    }

    public List<Threat> getBySeverity(String severity) {
        return threatRepository.findBySeverity(severity);
    }

    public List<Threat> getByStatus(String status) {
        return threatRepository.findByStatus(status);
    }

    // ===============================
    // AI Score
    // ===============================

    public List<Threat> getHighAiScore(Double score) {
        return threatRepository.findByAiScoreGreaterThan(score);
    }

    public List<Threat> getLowAiScore(Double score) {
        return threatRepository.findByAiScoreLessThan(score);
    }

    // ===============================
    // Dashboard Statistics
    // ===============================

    public long getTotalThreats() {
        return threatRepository.count();
    }

    public long getActiveThreats() {
        return threatRepository.countByStatus("ACTIVE");
    }

    public long getResolvedThreats() {
        return threatRepository.countByStatus("RESOLVED");
    }

    public long getHighSeverityThreats() {
        return threatRepository.countBySeverity("HIGH");
    }

    public long getMediumSeverityThreats() {
        return threatRepository.countBySeverity("MEDIUM");
    }

    public long getLowSeverityThreats() {
        return threatRepository.countBySeverity("LOW");
    }

    // ===============================
    // Update
    // ===============================

    public Threat updateThreat(Threat threat) {
        return threatRepository.save(threat);
    }

    // ===============================
    // Delete
    // ===============================

    public void deleteThreat(Long id) {
        threatRepository.deleteById(id);
    }

}