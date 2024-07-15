package com.example.ticketBookingPlatform.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketBookingPlatform.Entity.UserEntity;
import com.example.ticketBookingPlatform.Repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<UserEntity> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public UserEntity createUser(UserEntity user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
