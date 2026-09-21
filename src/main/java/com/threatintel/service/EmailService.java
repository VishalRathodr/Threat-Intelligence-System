package com.threatintel.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlert(String to,
                          String attackType,
                          String ip,
                          String riskLevel) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);

        message.setSubject("Threat Intelligence Alert");

        message.setText(
                "High Risk Attack Detected\n\n" +
                "Attack Type : " + attackType + "\n" +
                "IP Address  : " + ip + "\n" +
                "Risk Level  : " + riskLevel + "\n\n" +
                "Please investigate immediately."
        );

        mailSender.send(message);
    }
}