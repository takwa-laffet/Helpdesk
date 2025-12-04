package com.helpdesk.helpdesk_backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "User Data Transfer Object")
public class UserDTO {
    @Schema(description = "User ID", example = "1")
    private Long id;
    
    @Schema(description = "Username", example = "john.doe")
    private String username;
    
    @Schema(description = "Email address", example = "john.doe@company.com")
    private String email;
    
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;
    
    @Schema(description = "User role", example = "USER", allowableValues = {"ADMIN", "TECHNICIAN", "USER"})
    private String role;
    
    @Schema(description = "Department", example = "IT")
    private String department;
    
    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;
    
    @Schema(description = "Account status", example = "true")
    private Boolean isActive;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}