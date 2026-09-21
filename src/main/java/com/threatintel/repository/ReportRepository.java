package com.threatintel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threatintel.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByOrderByGeneratedDateDesc();

    List<Report> findByReportType(String reportType);

    List<Report> findByGeneratedBy(String generatedBy);

    List<Report> findByStatus(String status);

    long countByStatus(String status);

}