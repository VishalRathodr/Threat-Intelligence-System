package com.threatintel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threatintel.entity.LoginHistory;
import com.threatintel.repository.LoginHistoryRepository;

@Service
public class LoginHistoryService {

    private final LoginHistoryRepository repository;

    public LoginHistoryService(LoginHistoryRepository repository){
        this.repository=repository;
    }

    public LoginHistory save(LoginHistory login){
        return repository.save(login);
    }

    public List<LoginHistory> getAll(){
        return repository.findAllByOrderByLoginTimeDesc();
    }

}