package com.helpdesk.helpdesk_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Create Ticket Request")
public class CreateTicketDTO {
    @NotBlank(message = "Title is required")
    @Schema(description = "Ticket title", example = "Printer not working", required = true)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Ticket description", example = "The printer in room 101 is not printing documents", required = true)
    private String description;
    
    @Schema(description = "Priority level", example = "MEDIUM", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"}, defaultValue = "MEDIUM")
    private String priority = "MEDIUM";
    
    @Schema(description = "Category", example = "Hardware")
    private String category;
    
    @Schema(description = "Sub-category", example = "Printer")
    private String subCategory;
    
    @NotNull(message = "Created by user ID is required")
    @Schema(description = "ID of user creating the ticket", example = "1", required = true)
    private Long createdById;
    
    @Schema(description = "ID of user to assign the ticket to", example = "2")
    private Long assignedToId;
    
    @Schema(description = "Due date for resolution")
    private LocalDateTime dueDate;
}