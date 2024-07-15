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

import com.example.ticketBookingPlatform.Entity.ParkEntity;
import com.example.ticketBookingPlatform.Repository.ParkRepository;
import com.example.ticketBookingPlatform.Service.ParkService;

public class ParkServiceTest {

	@Mock
	private ParkRepository parkRepository;

	@InjectMocks
	private ParkService parkService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllParks() {
		// Mocking repository response
		ParkEntity park1 = new ParkEntity(1L, "Park A", "Location A", 4.0);
		ParkEntity park2 = new ParkEntity(2L, "Park B", "Location B", 4.5);
		List<ParkEntity> parkList = Arrays.asList(park1, park2);
		when(parkRepository.findAll()).thenReturn(parkList);

		// Calling service method
		List<ParkEntity> result = parkService.getAllParks();

		// Assertions
		assertEquals(2, result.size());
		assertTrue(result.contains(park1));
		assertTrue(result.contains(park2));
	}

	@Test
	public void testGetParkById() {
		// Mocking repository response
		Long parkId = 1L;
		ParkEntity park = new ParkEntity(parkId, "Park A", "Location A", 4.0);
		when(parkRepository.findById(parkId)).thenReturn(Optional.of(park));

		// Calling service method
		Optional<ParkEntity> result = parkService.getParkById(parkId);

		// Assertions
		assertTrue(result.isPresent());
		assertEquals(park.getName(), result.get().getName());
	}

	@Test
	public void testGetParkByIdNotFound() {
		// Mocking repository response
		Long parkId = 1L;
		when(parkRepository.findById(parkId)).thenReturn(Optional.empty());

		// Calling service method
		Optional<ParkEntity> result = parkService.getParkById(parkId);

		// Assertions
		assertFalse(result.isPresent());
	}

	@Test
	public void testCreatePark() {
		// Mocking repository save
		ParkEntity parkToCreate = new ParkEntity(null, "New Park", "New Location", 4.5);
		ParkEntity createdPark = new ParkEntity(1L, "New Park", "New Location", 4.5);
		when(parkRepository.save(parkToCreate)).thenReturn(createdPark);

		// Calling service method
		ParkEntity result = parkService.createPark(parkToCreate);

		// Assertions
		assertNotNull(result);
		assertEquals(createdPark.getId(), result.getId());
	}

	@Test
	public void testDeletePark() {
		Long parkId = 1L;

		// Calling service method
		parkService.deletePark(parkId);

		// Verifying repository method call
		verify(parkRepository, times(1)).deleteById(parkId);
	}
}
