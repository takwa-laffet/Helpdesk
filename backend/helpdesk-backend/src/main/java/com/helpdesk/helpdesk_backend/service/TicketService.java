package com.helpdesk.helpdesk_backend.service;

import com.helpdesk.helpdesk_backend.dto.*;
import com.helpdesk.helpdesk_backend.entity.*;
import com.helpdesk.helpdesk_backend.enums.TicketPriority;
import com.helpdesk.helpdesk_backend.enums.TicketStatus;
import com.helpdesk.helpdesk_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    
    public TicketDTO createTicket(CreateTicketDTO createTicketDTO) {
        User createdBy = userRepository.findById(createTicketDTO.getCreatedById())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + createTicketDTO.getCreatedById()));
        
        Ticket ticket = new Ticket();
        ticket.setTitle(createTicketDTO.getTitle());
        ticket.setDescription(createTicketDTO.getDescription());
        ticket.setPriority(createTicketDTO.getPriority());
        ticket.setCategory(createTicketDTO.getCategory());
        ticket.setSubCategory(createTicketDTO.getSubCategory());
        ticket.setCreatedBy(createdBy);
        ticket.setStatus("OPEN");
        
        if (createTicketDTO.getAssignedToId() != null) {
            User assignedTo = userRepository.findById(createTicketDTO.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + createTicketDTO.getAssignedToId()));
            ticket.setAssignedTo(assignedTo);
        }
        
        if (createTicketDTO.getDueDate() != null) {
            ticket.setDueDate(createTicketDTO.getDueDate());
        }
        
        Ticket savedTicket = ticketRepository.save(ticket);
        return convertToDTO(savedTicket);
    }
    
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return convertToDTO(ticket);
    }
    
    public TicketDTO getTicketByNumber(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
            .orElseThrow(() -> new RuntimeException("Ticket not found with number: " + ticketNumber));
        return convertToDTO(ticket);
    }
    
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TicketDTO> getTicketsByStatus(String status) {
        return ticketRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TicketDTO> getTicketsByPriority(String priority) {
        return ticketRepository.findByPriority(priority).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TicketDTO> getTicketsByUser(Long userId) {
        return ticketRepository.findByCreatedById(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<TicketDTO> getAssignedTickets(Long userId) {
        return ticketRepository.findByAssignedToId(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public TicketDTO updateTicketStatus(Long id, String status, String resolutionNotes) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        
        ticket.setStatus(status);
        if (resolutionNotes != null) {
            ticket.setResolutionNotes(resolutionNotes);
        }
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        return convertToDTO(updatedTicket);
    }
    
    public TicketDTO assignTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        ticket.setAssignedTo(user);
        ticket.setStatus("IN_PROGRESS");
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        return convertToDTO(updatedTicket);
    }
    
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }
    
    public DashboardStatsDTO getDashboardStats(Long userId) {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        stats.setTotalTickets(ticketRepository.count());
        stats.setOpenTickets(ticketRepository.countByStatus("OPEN"));
        stats.setInProgressTickets(ticketRepository.countByStatus("IN_PROGRESS"));
        stats.setResolvedTickets(ticketRepository.countByStatus("RESOLVED"));
        stats.setClosedTickets(ticketRepository.countByStatus("CLOSED"));
        stats.setHighPriorityTickets(
            ticketRepository.countByPriority("HIGH") + 
            ticketRepository.countByPriority("CRITICAL")
        );
        
        // Overdue tickets
        stats.setOverdueTickets(ticketRepository.countOverdueTickets(LocalDateTime.now()));
        
        // Assigned to me tickets
        if (userId != null) {
            stats.setAssignedToMeTickets((long) ticketRepository.findByAssignedToId(userId).size());
        } else {
            stats.setAssignedToMeTickets(0L);
        }
        
        return stats;
    }
    
    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(ticket.getPriority());
        dto.setStatus(ticket.getStatus());
        dto.setCategory(ticket.getCategory());
        dto.setSubCategory(ticket.getSubCategory());
        
        // Convert createdBy user to DTO
        UserDTO createdByDTO = new UserDTO();
        createdByDTO.setId(ticket.getCreatedBy().getId());
        createdByDTO.setUsername(ticket.getCreatedBy().getUsername());
        createdByDTO.setFullName(ticket.getCreatedBy().getFullName());
        createdByDTO.setEmail(ticket.getCreatedBy().getEmail());
        createdByDTO.setRole(ticket.getCreatedBy().getRole());
        dto.setCreatedBy(createdByDTO);
        
        // Convert assignedTo user to DTO if exists
        if (ticket.getAssignedTo() != null) {
            UserDTO assignedToDTO = new UserDTO();
            assignedToDTO.setId(ticket.getAssignedTo().getId());
            assignedToDTO.setUsername(ticket.getAssignedTo().getUsername());
            assignedToDTO.setFullName(ticket.getAssignedTo().getFullName());
            assignedToDTO.setEmail(ticket.getAssignedTo().getEmail());
            assignedToDTO.setRole(ticket.getAssignedTo().getRole());
            dto.setAssignedTo(assignedToDTO);
        }
        
        dto.setDueDate(ticket.getDueDate());
        dto.setResolutionNotes(ticket.getResolutionNotes());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());
        
        return dto;
    }
}