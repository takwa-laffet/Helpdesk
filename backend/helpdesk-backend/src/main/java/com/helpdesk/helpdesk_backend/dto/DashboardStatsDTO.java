package com.helpdesk.helpdesk_backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dashboard Statistics")
public class DashboardStatsDTO {
    @Schema(description = "Total tickets", example = "100")
    private Long totalTickets;
    
    @Schema(description = "Open tickets", example = "25")
    private Long openTickets;
    
    @Schema(description = "In progress tickets", example = "15")
    private Long inProgressTickets;
    
    @Schema(description = "Resolved tickets", example = "45")
    private Long resolvedTickets;
    
    @Schema(description = "Closed tickets", example = "15")
    private Long closedTickets;
    
    @Schema(description = "High priority tickets", example = "10")
    private Long highPriorityTickets;
    
    @Schema(description = "Overdue tickets", example = "5")
    private Long overdueTickets;
    
    @Schema(description = "Tickets assigned to current user", example = "8")
    private Long assignedToMeTickets;
}