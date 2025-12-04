package com.helpdesk.helpdesk_backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    @NotBlank(message = "Comment content is required")
    private String content;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Ticket ID is required")
    private Long ticketId;
    
    private Boolean isInternal = false;
}