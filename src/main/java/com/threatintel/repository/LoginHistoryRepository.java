package com.threatintel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threatintel.entity.LoginHistory;

public interface LoginHistoryRepository
extends JpaRepository<LoginHistory,Long>{

    List<LoginHistory> findAllByOrderByLoginTimeDesc();

}