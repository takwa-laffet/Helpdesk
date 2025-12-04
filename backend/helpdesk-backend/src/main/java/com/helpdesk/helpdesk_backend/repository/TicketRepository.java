package com.helpdesk.helpdesk_backend.repository;



import com.helpdesk.helpdesk_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.helpdesk.helpdesk_backend.enums.TicketPriority;
import com.helpdesk.helpdesk_backend.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    List<Ticket> findByCreatedById(Long userId);
    List<Ticket> findByAssignedToId(Long userId);
    List<Ticket> findByStatus(String status);
    List<Ticket> findByPriority(String priority);
    
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.priority = :priority")
    Long countByPriority(@Param("priority") String priority);
    
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.dueDate < :now AND t.status NOT IN ('RESOLVED', 'CLOSED')")
    Long countOverdueTickets(@Param("now") LocalDateTime now);
}