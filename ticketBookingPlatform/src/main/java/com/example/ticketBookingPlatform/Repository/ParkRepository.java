package com.example.ticketBookingPlatform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketBookingPlatform.Entity.ParkEntity;

@Repository
public interface ParkRepository extends JpaRepository<ParkEntity, Long> {
}
