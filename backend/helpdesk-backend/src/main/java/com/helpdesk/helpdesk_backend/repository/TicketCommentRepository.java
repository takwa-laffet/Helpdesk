package com.helpdesk.helpdesk_backend.repository;


import com.helpdesk.helpdesk_backend.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
    List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
    Long countByTicketId(Long ticketId);
    void deleteByTicketId(Long ticketId);
}