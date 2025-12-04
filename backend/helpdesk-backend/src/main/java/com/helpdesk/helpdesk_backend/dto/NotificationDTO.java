package com.helpdesk.helpdesk_backend.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private Long userId;
    private Long ticketId;
    private String ticketNumber;
    private Boolean isRead;
    private String type;
    private LocalDateTime createdAt;
}