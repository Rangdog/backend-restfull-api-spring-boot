package com.fingerprint.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @GetMapping("api/logout-success")
    public String logoutSuccess() {
        return "You have been logged out successfully.";
    }
}
