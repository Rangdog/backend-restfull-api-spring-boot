package com.fingerprint.demo.controller;

import com.fingerprint.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/other")
public class OrtherController {
    @Autowired
    private MyService myService;
    @GetMapping
    public ResponseEntity<String> resetFingerPrint(){
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://localhost:5000/api/reset";
        try {
            // Tạo headers nếu cần thiết
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Gửi yêu cầu POST đến API Flask
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // Bước 2: Reset database
                myService.resetDatabase(); // Gọi service để reset database
                return ResponseEntity.ok("Fingerprint data and database have been reset successfully.");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to reset fingerprint data.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
