package com.threatintel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ATTACK_LOGS")
public class AttackLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attack_seq")
    @SequenceGenerator(name = "attack_seq", sequenceName = "ATTACK_LOG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "IP_ADDRESS", nullable = false, length = 50)
    private String ipAddress;

    @Column(name = "USERNAME", length = 100)
    private String username;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @Column(name = "COUNTRY", nullable = false, length = 100)
    private String country;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "REGION", length = 100)
    private String region;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ATTACK_TYPE", nullable = false, length = 50)
    private String attackType;

    @Column(name = "RISK_LEVEL", nullable = false, length = 20)
    private String riskLevel;

    @Column(name = "REQUEST_METHOD", nullable = false, length = 10)
    private String requestMethod;

    @Column(name = "REQUEST_URI", length = 500)
    private String requestUri;

    @Column(name = "USER_AGENT", length = 1000)
    private String userAgent;

    @Column(name = "BROWSER", length = 100)
    private String browser;

    @Column(name = "OPERATING_SYSTEM", length = 100)
    private String operatingSystem;

    @Column(name = "DEVICE_TYPE", length = 50)
    private String deviceType;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;

    public AttackLog() {}

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if (riskLevel == null || riskLevel.isEmpty()) {
            riskLevel = "LOW";
        }
        if (country == null) country = "Unknown";
        if (city == null) city = "Unknown";
    }

    // Getters and Setters
    public Long getId() { 
    	return id; 
    	}
    public void setId(Long id) { 
    	this.id = id; 
    	}
    public String getIpAddress() { 
    	return ipAddress; 
    	}
    public void setIpAddress(String ipAddress) {
    	this.ipAddress = ipAddress; 
    	}
    public String getUsername() {
    	return username; 
    	}
    public void setUsername(String username) { 
    	this.username = username; 
    	}
    public String getPassword() {
    	return password; 
    	}
    public void setPassword(String password) {
    	this.password = password; 
    	}
    public String getCountry() { 
    	return country;
    	}
    public void setCountry(String country) {
    	this.country = country; 
    	}
    public String getCity() { 
    	return city; 
    	}
    public void setCity(String city) {
    	this.city = city;
    	}
    public String getRegion() { 
    	return region; 
    	}
    public void setRegion(String region) {
    	this.region = region; 
    	}
    public Double getLatitude() {
    	return latitude; 
    	}
    public void setLatitude(Double latitude) { 
    	this.latitude = latitude;
    	}
    public Double getLongitude() {
    	return longitude;
    	}
    public void setLongitude(Double longitude) { 
    	this.longitude = longitude;
    	}
    public String getAttackType() { 
    	return attackType; 
    	}
    public void setAttackType(String attackType) {
    	this.attackType = attackType; 
    	}
    public String getRiskLevel() { 
    	return riskLevel;
    	}
    public void setRiskLevel(String riskLevel) { 
    	this.riskLevel = riskLevel;
    	}
    public String getRequestMethod() {
    	return requestMethod; 
    	}
    public void setRequestMethod(String requestMethod) {
    	this.requestMethod = requestMethod; 
    	}
    public String getRequestUri() { 
    	return requestUri;
    	}
    public void setRequestUri(String requestUri) {
    	this.requestUri = requestUri; 
    	} // YE FIX KIYA
    public String getUserAgent() {
    	return userAgent; 
    	}
    public void setUserAgent(String userAgent) { 
    	this.userAgent = userAgent;
    	}
    public String getBrowser() { 
    	return browser; 
    	}
    public void setBrowser(String browser) { 
    	this.browser = browser;
    	}
    public String getOperatingSystem() {
    	return operatingSystem; 
    	}
    public void setOperatingSystem(String operatingSystem) { 
    	this.operatingSystem = operatingSystem;
    	}
    public String getDeviceType() { 
    	return deviceType;
    	}
    public void setDeviceType(String deviceType) {
    	this.deviceType = deviceType;
    	}
    public LocalDateTime getTimestamp() {
    	return timestamp;
    	}
    public void setTimestamp(LocalDateTime timestamp) { 
    	this.timestamp = timestamp; 
    	}
}