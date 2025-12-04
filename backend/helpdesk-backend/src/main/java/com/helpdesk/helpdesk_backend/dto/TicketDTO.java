package com.helpdesk.helpdesk_backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Ticket Data Transfer Object")
public class TicketDTO {
    @Schema(description = "Ticket ID", example = "1")
    private Long id;
    
    @Schema(description = "Ticket number", example = "TICK1701690123456")
    private String ticketNumber;
    
    @Schema(description = "Ticket title", example = "Printer not working")
    private String title;
    
    @Schema(description = "Ticket description", example = "The printer in room 101 is not printing")
    private String description;
    
    @Schema(description = "Priority level", example = "HIGH", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"})
    private String priority;
    
    @Schema(description = "Current status", example = "OPEN", allowableValues = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED", "PENDING"})
    private String status;
    
    @Schema(description = "Category", example = "Hardware")
    private String category;
    
    @Schema(description = "Sub-category", example = "Printer")
    private String subCategory;
    
    @Schema(description = "User who created the ticket")
    private UserDTO createdBy;
    
    @Schema(description = "User assigned to the ticket")
    private UserDTO assignedTo;
    
    @Schema(description = "Due date for resolution")
    private LocalDateTime dueDate;
    
    @Schema(description = "Resolution notes")
    private String resolutionNotes;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}