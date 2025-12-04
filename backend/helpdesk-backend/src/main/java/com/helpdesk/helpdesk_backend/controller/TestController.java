package com.helpdesk.helpdesk_backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
@Tag(name = "Test", description = "Test endpoints for API verification")
public class TestController {
    
    @GetMapping("/hello")
    @Operation(summary = "Hello endpoint", description = "Returns a hello message to verify API is working")
    public String hello() {
        return "🚀 Helpdesk Backend API is running! | " + new java.util.Date();
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns API health status")
    public String health() {
        return """
               ✅ API Status: Healthy
               📅 Time: %s
               🏗️ Version: 1.0.0
               """.formatted(new java.util.Date());
    }
}