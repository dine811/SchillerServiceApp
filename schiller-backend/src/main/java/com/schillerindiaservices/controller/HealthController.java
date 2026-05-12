package com.schillerindiaservices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check controller - Demo endpoint to verify the API is running.
 * Replace this with actual module controllers (ServiceController,
 * EmployeeController, etc.)
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "app", "Schiller India Services API",
                "timestamp", LocalDateTime.now().toString()));
    }
}
