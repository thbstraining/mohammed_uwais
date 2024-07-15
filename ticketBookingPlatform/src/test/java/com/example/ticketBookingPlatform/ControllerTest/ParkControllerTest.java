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

import com.example.ticketBookingPlatform.Controller.ParkController;
import com.example.ticketBookingPlatform.Entity.ParkEntity;
import com.example.ticketBookingPlatform.Service.ParkService;

public class ParkControllerTest {

	@Mock
	private ParkService parkService;

	@InjectMocks
	private ParkController parkController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllParks() {
		// Mocking the service method
		ParkEntity park1 = new ParkEntity(1L, "Park A", "Location A", 4.0);
		ParkEntity park2 = new ParkEntity(2L, "Park B", "Location B", 4.5);
		List<ParkEntity> parkList = Arrays.asList(park1, park2);
		when(parkService.getAllParks()).thenReturn(parkList);

		// Calling the controller method
		ResponseEntity<List<ParkEntity>> response = parkController.getAllParks();

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertTrue(response.getBody().contains(park1));
		assertTrue(response.getBody().contains(park2));
	}

	@Test
	public void testGetParkById() {
		// Mocking the service method
		Long parkId = 1L;
		ParkEntity park = new ParkEntity(parkId, "Park A", "Location A", 4.0);
		when(parkService.getParkById(parkId)).thenReturn(Optional.of(park));

		// Calling the controller method
		ResponseEntity<ParkEntity> response = parkController.getParkById(parkId);

		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(park.getName(), response.getBody().getName());
	}

	@Test
	public void testGetParkByIdNotFound() {
		// Mocking the service method to return Optional.empty()
		Long parkId = 1L;
		when(parkService.getParkById(parkId)).thenReturn(Optional.empty());

		// Calling the controller method
		ResponseEntity<ParkEntity> response = parkController.getParkById(parkId);

		// Assertions
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testCreatePark() {
		// Mocking the service method
		ParkEntity parkToCreate = new ParkEntity(null, "New Park", "New Location", 3.5);
		ParkEntity createdPark = new ParkEntity(1L, "New Park", "New Location", 3.5);
		when(parkService.createPark(parkToCreate)).thenReturn(createdPark);

		// Calling the controller method
		ResponseEntity<ParkEntity> response = parkController.createPark(parkToCreate);

		// Assertions
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdPark.getId(), response.getBody().getId());
	}

	@Test
	public void testDeletePark() {
		Long parkId = 1L;

		// Calling the controller method
		ResponseEntity<Void> response = parkController.deletePark(parkId);

		// Assertions
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(parkService, times(1)).deletePark(parkId);
	}
}
