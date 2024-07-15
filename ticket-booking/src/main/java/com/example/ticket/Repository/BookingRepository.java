package com.example.ticket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket.Entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
