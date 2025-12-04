package com.helpdesk.helpdesk_backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Create User Request")
public class CreateUserDTO {
    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example = "john.doe", required = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Schema(description = "Password", example = "password123", required = true)
    private String password;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(description = "Email address", example = "john.doe@company.com", required = true)
    private String email;
    
    @NotBlank(message = "Full name is required")
    @Schema(description = "Full name", example = "John Doe", required = true)
    private String fullName;
    
    @Schema(description = "User role", example = "USER", allowableValues = {"ADMIN", "TECHNICIAN", "USER"}, defaultValue = "USER")
    private String role = "USER";
    
    @Schema(description = "Department", example = "IT")
    private String department;
    
    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;
}