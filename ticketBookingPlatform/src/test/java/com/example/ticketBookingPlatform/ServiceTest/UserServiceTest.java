package com.example.ticketBookingPlatform.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.ticketBookingPlatform.Entity.UserEntity;
import com.example.ticketBookingPlatform.Repository.UserRepository;
import com.example.ticketBookingPlatform.Service.UserService;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllUsers() {
		// Mocking repository response
		UserEntity user1 = new UserEntity(1L, "user1", "password1", "user1@example.com");
		UserEntity user2 = new UserEntity(2L, "user2", "password2", "user2@example.com");
		List<UserEntity> userList = Arrays.asList(user1, user2);
		when(userRepository.findAll()).thenReturn(userList);

		// Calling service method
		List<UserEntity> result = userService.getAllUsers();

		// Assertions
		assertEquals(2, result.size());
		assertTrue(result.contains(user1));
		assertTrue(result.contains(user2));
	}

	@Test
	public void testGetUserById() {
		// Mocking repository response
		Long userId = 1L;
		UserEntity user = new UserEntity(userId, "user1", "password1", "user1@example.com");
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Calling service method
		Optional<UserEntity> result = userService.getUserById(userId);

		// Assertions
		assertTrue(result.isPresent());
		assertEquals(user.getUsername(), result.get().getUsername());
	}

	@Test
	public void testGetUserByIdNotFound() {
		// Mocking repository response
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Calling service method
		Optional<UserEntity> result = userService.getUserById(userId);

		// Assertions
		assertFalse(result.isPresent());
	}

	@Test
	public void testCreateUser() {
		// Mocking repository save
		UserEntity userToCreate = new UserEntity(null, "newUser", "newPassword", "newuser@example.com");
		UserEntity createdUser = new UserEntity(1L, "newUser", "newPassword", "newuser@example.com");
		when(userRepository.save(userToCreate)).thenReturn(createdUser);

		// Calling service method
		UserEntity result = userService.createUser(userToCreate);

		// Assertions
		assertNotNull(result);
		assertEquals(createdUser.getId(), result.getId());
	}

	@Test
	public void testDeleteUser() {
		Long userId = 1L;

		// Calling service method
		userService.deleteUser(userId);

		// Verifying repository method call
		verify(userRepository, times(1)).deleteById(userId);
	}
}
