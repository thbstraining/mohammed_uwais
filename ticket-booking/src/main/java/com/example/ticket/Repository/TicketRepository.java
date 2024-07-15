package com.example.ticket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket.Entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
