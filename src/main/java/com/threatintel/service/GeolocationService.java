package com.threatintel.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class GeolocationService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Free API: ip-api.com
    private static final String GEO_URL = "http://ip-api.com/json/";

    public Map<String, Object> getLocation(String ip) {
        try {
            // localhost ke liye skip
            if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
                ip = "8.8.8.8"; // test ke liye Google DNS
            }
            
            String url = GEO_URL + ip + "?fields=status,country,city,regionName,lat,lon";
            Map response = restTemplate.getForObject(url, Map.class);

            if (response != null && "success".equals(response.get("status"))) {
                return response;
            }
        } catch (Exception e) {
            System.out.println("Geo API Error: " + e.getMessage());
        }
        return null;
    }
}