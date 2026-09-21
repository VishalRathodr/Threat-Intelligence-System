package com.threatintel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.threatintel.service.LoginHistoryService;

@Controller
public class LoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    public LoginHistoryController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    @GetMapping("/admin/login-history")
    public String loginHistory(Model model) {

        model.addAttribute(
                "history",
                loginHistoryService.getAll());

        return "admin/login-history";
    }
}