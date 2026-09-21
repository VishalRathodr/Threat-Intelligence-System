package com.threatintel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.Threat;

@Repository
public interface ThreatRepository extends JpaRepository<Threat, Long> {

    // =========================
    // Dashboard
    // =========================

    long count();

    long countByStatus(String status);

    long countBySeverity(String severity);

    // =========================
    // Search
    // =========================

    List<Threat> findByIpAddress(String ipAddress);

    List<Threat> findByThreatName(String threatName);

    List<Threat> findByAttackType(String attackType);

    List<Threat> findBySeverity(String severity);

    List<Threat> findByStatus(String status);

    // =========================
    // AI Score
    // =========================

    List<Threat> findByAiScoreGreaterThan(Double score);

    List<Threat> findByAiScoreLessThan(Double score);

    // =========================
    // Latest Threats
    // =========================

    List<Threat> findAllByOrderByCreatedAtDesc();

}