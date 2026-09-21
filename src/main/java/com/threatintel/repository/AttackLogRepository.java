package com.threatintel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.AttackLog;

@Repository
public interface AttackLogRepository extends JpaRepository<AttackLog, Long> {

	// =========================
	// Dashboard
	// =========================

	long countByRiskLevel(String riskLevel);

	long countByAttackType(String attackType);

	long countByCountry(String country);

	// =========================
	// Search
	// =========================

	List<AttackLog> findByIpAddress(String ipAddress);

	List<AttackLog> findByCountry(String country);

	List<AttackLog> findByCity(String city);

	List<AttackLog> findByAttackType(String attackType);

	List<AttackLog> findByRiskLevel(String riskLevel);

	List<AttackLog> findByRequestMethod(String requestMethod);

	// =========================
	// Time Filter
	// =========================

	List<AttackLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

	// =========================
	// Latest Records
	// =========================

	List<AttackLog> findAllByOrderByTimestampDesc();

	List<AttackLog> findAllByOrderByIdDesc(Pageable pageable);

	List<AttackLog> findByRiskLevelOrderByTimestampDesc(String riskLevel);

}
