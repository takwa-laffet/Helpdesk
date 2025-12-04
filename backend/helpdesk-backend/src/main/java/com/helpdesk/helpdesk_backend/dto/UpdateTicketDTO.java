package com.helpdesk.helpdesk_backend.dto;

import com.helpdesk.helpdesk_backend.enums.TicketPriority;
import com.helpdesk.helpdesk_backend.enums.TicketStatus;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateTicketDTO {
    private String title;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private String category;
    private String subCategory;
    private Long assignedToId;
    private LocalDateTime dueDate;
    private String resolutionNotes;
}