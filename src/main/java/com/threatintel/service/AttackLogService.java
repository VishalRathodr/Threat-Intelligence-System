package com.threatintel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threatintel.entity.AttackLog;
import com.threatintel.repository.AttackLogRepository;

@Service
public class AttackLogService {

    private final AttackLogRepository attackLogRepository;

    public AttackLogService(AttackLogRepository attackLogRepository) {
        this.attackLogRepository = attackLogRepository;
    }

    // ===============================
    // Save Attack Log
    // ===============================

    public AttackLog saveAttack(AttackLog attackLog) {

        if (attackLog.getTimestamp() == null) {
            attackLog.setTimestamp(LocalDateTime.now());
        }

        return attackLogRepository.save(attackLog);
    }

    // ===============================
    // Get All Attack Logs
    // ===============================

    public List<AttackLog> getAllAttacks() {
        return attackLogRepository.findAllByOrderByTimestampDesc();
    }

    // ===============================
    // Get Attack By ID
    // ===============================

    public AttackLog getAttackById(Long id) {

        return attackLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attack Log Not Found"));

    }

    // ===============================
    // Search
    // ===============================

    public List<AttackLog> getByIp(String ip) {
        return attackLogRepository.findByIpAddress(ip);
    }

    public List<AttackLog> getByCountry(String country) {
        return attackLogRepository.findByCountry(country);
    }

    public List<AttackLog> getByCity(String city) {
        return attackLogRepository.findByCity(city);
    }

    public List<AttackLog> getByAttackType(String attackType) {
        return attackLogRepository.findByAttackType(attackType);
    }

    public List<AttackLog> getByRiskLevel(String riskLevel) {
        return attackLogRepository.findByRiskLevel(riskLevel);
    }

    // ===============================
    // Dashboard Statistics
    // ===============================

    public long getTotalAttacks() {
        return attackLogRepository.count();
    }

    public long getHighRiskAttacks() {
        return attackLogRepository.countByRiskLevel("HIGH");
    }

    public long getMediumRiskAttacks() {
        return attackLogRepository.countByRiskLevel("MEDIUM");
    }

    public long getLowRiskAttacks() {
        return attackLogRepository.countByRiskLevel("LOW");
    }

    public long getWordPressAttacks() {
        return attackLogRepository.countByAttackType("WordPress Attack");
    }

    public long getSqlInjectionAttacks() {
        return attackLogRepository.countByAttackType("SQL Injection");
    }

    public long getXssAttacks() {
        return attackLogRepository.countByAttackType("XSS");
    }

    // ===============================
    // Latest 10 Attacks
    // ===============================

    public List<AttackLog> getLatestAttacks() {

        Pageable pageable = PageRequest.of(0, 10);

        return attackLogRepository.findAllByOrderByIdDesc(pageable);

    }

    // ===============================
    // High Risk Attack Logs
    // ===============================

    public List<AttackLog> getHighRiskAttackLogs() {
        return attackLogRepository.findByRiskLevelOrderByTimestampDesc("HIGH");
    }

    // ===============================
    // Delete
    // ===============================

    public void deleteAttack(Long id) {
        attackLogRepository.deleteById(id);
    }

}