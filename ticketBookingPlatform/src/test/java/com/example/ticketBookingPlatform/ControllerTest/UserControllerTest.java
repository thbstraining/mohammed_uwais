package com.example.ticketBookingPlatform.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ticketBookingPlatform.Controller.UserController;
import com.example.ticketBookingPlatform.Entity.UserEntity;
import com.example.ticketBookingPlatform.Service.UserService;

public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllUsers() {
		// Mocking the service method
		UserEntity user1 = new UserEntity(1L, "user1", "password1", "user1@example.com");
		UserEntity user2 = new UserEntity(2L, "user2", "password2", "user2@example.com");
		List<UserEntity> userList = Arrays.asList(user1, user2);
		when(userService.getAllUsers()).thenReturn(userList);

		// Calling the controller method
		ResponseEntity<List<UserEntity>> response = userController.getAllUsers();

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(user1));
		assertTrue(response.getBody().contains(user2));
	}

	@Test
	public void testGetUserById() {
		// Mocking the service method
		Long userId = 1L;
		UserEntity user = new UserEntity(userId, "user1", "password1", "user1@example.com");
		when(userService.getUserById(userId)).thenReturn(Optional.of(user));

		// Calling the controller method
		ResponseEntity<UserEntity> response = userController.getUserById(userId);

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user.getUsername(), response.getBody().getUsername());
	}

	@Test
	public void testGetUserByIdNotFound() {
		// Mocking the service method to return Optional.empty()
		Long userId = 1L;
		when(userService.getUserById(userId)).thenReturn(Optional.empty());

		// Calling the controller method
		ResponseEntity<UserEntity> response = userController.getUserById(userId);

		// Assertions
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testCreateUser() {
		// Mocking the service method
		UserEntity userToCreate = new UserEntity(null, "newUser", "newPassword", "newuser@example.com");
		UserEntity createdUser = new UserEntity(1L, "newUser", "newPassword", "newuser@example.com");
		when(userService.createUser(userToCreate)).thenReturn(createdUser);

		// Calling the controller method
		ResponseEntity<UserEntity> response = userController.createUser(userToCreate);

		// Assertions
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdUser.getId(), response.getBody().getId());
	}

	@Test
	public void testDeleteUser() {
		Long userId = 1L;

		// Calling the controller method
		ResponseEntity<Void> response = userController.deleteUser(userId);

		// Assertions
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(userService, times(1)).deleteUser(userId);
	}
}
