package com.example.ticketBookingPlatform.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticketBookingPlatform.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);

	List<UserEntity> findAll();
}
