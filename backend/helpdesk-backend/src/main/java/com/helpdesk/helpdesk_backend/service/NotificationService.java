package com.helpdesk.helpdesk_backend.service;

import com.helpdesk.helpdesk_backend.dto.NotificationDTO;
import com.helpdesk.helpdesk_backend.entity.Notification;
import com.helpdesk.helpdesk_backend.entity.Ticket;
import com.helpdesk.helpdesk_backend.entity.User;
import com.helpdesk.helpdesk_backend.repository.NotificationRepository;
import com.helpdesk.helpdesk_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    
    public void sendTicketCreatedNotification(Ticket ticket) {
        String title = "New Ticket Created: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been created by %s",
            ticket.getTicketNumber(), ticket.getCreatedBy().getFullName());
        
        // Create notification for admins and technicians
        List<User> recipients = userRepository.findAll().stream()
            .filter(user -> "ADMIN".equals(user.getRole()) || "TECHNICIAN".equals(user.getRole()))
            .collect(Collectors.toList());
        
        for (User recipient : recipients) {
            createNotification(recipient, ticket, title, message, "TICKET_CREATED");
        }
        
        // Also notify the creator
        createNotification(ticket.getCreatedBy(), ticket, title, message, "TICKET_CREATED");
    }
    
    public void sendTicketUpdatedNotification(Ticket ticket) {
        String title = "Ticket Updated: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been updated",
            ticket.getTicketNumber());
        
        // Notify creator and assignee
        notifyUser(ticket.getCreatedBy(), ticket, title, message, "TICKET_UPDATED");
        
        if (ticket.getAssignedTo() != null) {
            notifyUser(ticket.getAssignedTo(), ticket, title, message, "TICKET_UPDATED");
        }
    }
    
    public void sendTicketAssignedNotification(Ticket ticket, User assignee) {
        String title = "Ticket Assigned: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been assigned to you",
            ticket.getTicketNumber());
        
        notifyUser(assignee, ticket, title, message, "TICKET_ASSIGNED");
    }
    
    public void sendCommentAddedNotification(Ticket ticket, User commenter, String comment) {
        String title = "New Comment on Ticket: " + ticket.getTitle();
        String message = String.format("%s added a comment to ticket #%s: %s",
            commenter.getFullName(), ticket.getTicketNumber(), 
            comment.length() > 50 ? comment.substring(0, 50) + "..." : comment);
        
        // Notify creator and assignee (excluding commenter)
        if (!ticket.getCreatedBy().getId().equals(commenter.getId())) {
            notifyUser(ticket.getCreatedBy(), ticket, title, message, "NEW_COMMENT");
        }
        
        if (ticket.getAssignedTo() != null && !ticket.getAssignedTo().getId().equals(commenter.getId())) {
            notifyUser(ticket.getAssignedTo(), ticket, title, message, "NEW_COMMENT");
        }
    }
    
    private void notifyUser(User user, Ticket ticket, String title, String message, String type) {
        createNotification(user, ticket, title, message, type);
    }
    
    private void createNotification(User user, Ticket ticket, String title, String message, String type) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setUser(user);
        notification.setTicket(ticket);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        
        notificationRepository.save(notification);
    }
    
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Long getUnreadNotificationCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
    
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        for (Notification notification : notifications) {
            notification.setIsRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
    
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
    
    @Transactional
    public void deleteAllNotifications(Long userId) {
        notificationRepository.deleteByUserId(userId);
    }
    
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setUserId(notification.getUser().getId());
        
        if (notification.getTicket() != null) {
            dto.setTicketId(notification.getTicket().getId());
            dto.setTicketNumber(notification.getTicket().getTicketNumber());
        }
        
        dto.setIsRead(notification.getIsRead());
        dto.setType(notification.getType());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }
    
    // Additional helper methods
    
    public void sendTicketResolvedNotification(Ticket ticket) {
        String title = "Ticket Resolved: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been resolved",
            ticket.getTicketNumber());
        
        // Notify creator
        notifyUser(ticket.getCreatedBy(), ticket, title, message, "TICKET_RESOLVED");
        
        // Notify assignee if exists
        if (ticket.getAssignedTo() != null) {
            notifyUser(ticket.getAssignedTo(), ticket, title, message, "TICKET_RESOLVED");
        }
    }
    
    public void sendTicketClosedNotification(Ticket ticket) {
        String title = "Ticket Closed: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been closed",
            ticket.getTicketNumber());
        
        // Notify creator
        notifyUser(ticket.getCreatedBy(), ticket, title, message, "TICKET_CLOSED");
        
        // Notify assignee if exists
        if (ticket.getAssignedTo() != null) {
            notifyUser(ticket.getAssignedTo(), ticket, title, message, "TICKET_CLOSED");
        }
    }
    
    public void sendTicketReopenedNotification(Ticket ticket) {
        String title = "Ticket Reopened: " + ticket.getTitle();
        String message = String.format("Ticket #%s has been reopened",
            ticket.getTicketNumber());
        
        // Notify creator
        notifyUser(ticket.getCreatedBy(), ticket, title, message, "TICKET_REOPENED");
        
        // Notify assignee if exists
        if (ticket.getAssignedTo() != null) {
            notifyUser(ticket.getAssignedTo(), ticket, title, message, "TICKET_REOPENED");
        }
        
        // Notify admins and technicians
        List<User> recipients = userRepository.findAll().stream()
            .filter(user -> "ADMIN".equals(user.getRole()) || "TECHNICIAN".equals(user.getRole()))
            .collect(Collectors.toList());
        
        for (User recipient : recipients) {
            if (!recipient.getId().equals(ticket.getCreatedBy().getId()) && 
                (ticket.getAssignedTo() == null || !recipient.getId().equals(ticket.getAssignedTo().getId()))) {
                createNotification(recipient, ticket, title, message, "TICKET_REOPENED");
            }
        }
    }
}