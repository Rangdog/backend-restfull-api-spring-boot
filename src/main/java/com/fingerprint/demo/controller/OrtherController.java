package com.fingerprint.demo.controller;

import com.fingerprint.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/other")
public class OrtherController {
    @Autowired
    private MyService myService;
    @PostMapping
    public ResponseEntity<String> resetFingerPrint(){
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://localhost:5000/api/reset";
        try {
            // Tạo headers nếu cần thiết
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            // Gửi yêu cầu POST đến API Flask
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, requestEntity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // Bước 2: Reset database
                myService.resetDatabase(); // Gọi service để reset database
                return ResponseEntity.ok("Fingerprint data and database have been reset successfully.");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to reset fingerprint data.");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
