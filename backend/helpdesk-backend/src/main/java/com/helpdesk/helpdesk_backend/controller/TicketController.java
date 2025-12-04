package com.helpdesk.helpdesk_backend.controller;


import com.helpdesk.helpdesk_backend.dto.*;
import com.helpdesk.helpdesk_backend.enums.TicketPriority;
import com.helpdesk.helpdesk_backend.enums.TicketStatus;
import com.helpdesk.helpdesk_backend.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.helpdesk.helpdesk_backend.dto.CreateTicketDTO;
import com.helpdesk.helpdesk_backend.dto.TicketDTO;
import com.helpdesk.helpdesk_backend.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Ticket management endpoints")
public class TicketController {
    private final TicketService ticketService;
    
    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Creates a new support ticket")
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody CreateTicketDTO createTicketDTO) {
        TicketDTO createdTicket = ticketService.createTicket(createTicketDTO);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Retrieves a ticket by its ID")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        TicketDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }
    
    @GetMapping("/number/{ticketNumber}")
    @Operation(summary = "Get ticket by number", description = "Retrieves a ticket by its ticket number")
    public ResponseEntity<TicketDTO> getTicketByNumber(@PathVariable String ticketNumber) {
        TicketDTO ticket = ticketService.getTicketByNumber(ticketNumber);
        return ResponseEntity.ok(ticket);
    }
    
    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieves all tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get tickets by status", description = "Retrieves tickets filtered by status")
    public ResponseEntity<List<TicketDTO>> getTicketsByStatus(@PathVariable String status) {
        List<TicketDTO> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get tickets by priority", description = "Retrieves tickets filtered by priority")
    public ResponseEntity<List<TicketDTO>> getTicketsByPriority(@PathVariable String priority) {
        List<TicketDTO> tickets = ticketService.getTicketsByPriority(priority);
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's tickets", description = "Retrieves tickets created by a specific user")
    public ResponseEntity<List<TicketDTO>> getUserTickets(@PathVariable Long userId) {
        List<TicketDTO> tickets = ticketService.getTicketsByUser(userId);
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/assigned/{userId}")
    @Operation(summary = "Get assigned tickets", description = "Retrieves tickets assigned to a specific user")
    public ResponseEntity<List<TicketDTO>> getAssignedTickets(@PathVariable Long userId) {
        List<TicketDTO> tickets = ticketService.getAssignedTickets(userId);
        return ResponseEntity.ok(tickets);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update ticket status", description = "Updates the status of a ticket")
    public ResponseEntity<TicketDTO> updateTicketStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String resolutionNotes) {
        TicketDTO updatedTicket = ticketService.updateTicketStatus(id, status, resolutionNotes);
        return ResponseEntity.ok(updatedTicket);
    }
    
    @PatchMapping("/{id}/assign/{userId}")
    @Operation(summary = "Assign ticket to user", description = "Assigns a ticket to a specific user")
    public ResponseEntity<TicketDTO> assignTicket(@PathVariable Long id, @PathVariable Long userId) {
        TicketDTO updatedTicket = ticketService.assignTicket(id, userId);
        return ResponseEntity.ok(updatedTicket);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ticket", description = "Deletes a ticket by ID")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}