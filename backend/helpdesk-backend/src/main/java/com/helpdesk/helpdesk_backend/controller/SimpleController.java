package com.helpdesk.helpdesk_backend.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SimpleController {
    
    @GetMapping("/test")
    public String test() {
        return "Helpdesk Backend is working! " + new java.util.Date();
    }
    
    @GetMapping("/health")
    public String health() {
        return "Status: OK";
    }
    
    @PostMapping("/echo")
    public EchoResponse echo(@RequestBody EchoRequest request) {
        return new EchoResponse("Echo: " + request.getMessage());
    }
    
    // Simple request/response classes
    static class EchoRequest {
        private String message;
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    static class EchoResponse {
        private String response;
        
        public EchoResponse(String response) {
            this.response = response;
        }
        
        public String getResponse() {
            return response;
        }
        
        public void setResponse(String response) {
            this.response = response;
        }
    }
}