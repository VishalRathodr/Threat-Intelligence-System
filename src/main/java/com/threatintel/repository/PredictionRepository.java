package com.threatintel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findAllByOrderByPredictionTimeDesc();

    List<Prediction> findByIpAddress(String ipAddress);

    List<Prediction> findByPredictedAttack(String predictedAttack);

    List<Prediction> findByRiskLevel(String riskLevel);

    List<Prediction> findByModelName(String modelName);

    List<Prediction> findByPredictionResult(String predictionResult);

    List<Prediction> findByConfidenceGreaterThan(Double confidence);

    List<Prediction> findByConfidenceLessThan(Double confidence);

    long countByRiskLevel(String riskLevel);

    long countByPredictionResult(String predictionResult);
}